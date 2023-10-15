package com.devh.cafe.api.menu.service

import com.devh.cafe.api.common.paging.Paging
import com.devh.cafe.api.menu.controller.request.OptionCreateRequest
import com.devh.cafe.api.menu.controller.request.OptionGetRequest
import com.devh.cafe.api.menu.controller.response.OptionData
import com.devh.cafe.api.menu.controller.response.OptionPageData
import com.devh.cafe.api.menu.controller.response.SubOptionData
import com.devh.cafe.api.menu.exception.categoryDoesNotExists
import com.devh.cafe.api.menu.exception.menuDoesNotExists
import com.devh.cafe.api.menu.exception.optionAlreadyExists
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.repository.MenuRepository
import com.devh.cafe.api.menu.repository.OptionRepository
import com.devh.cafe.infrastructure.database.entity.Option
import com.devh.cafe.infrastructure.database.entity.SubOption
import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OptionServiceImpl(
    @Autowired
    val categoryRepository: CategoryRepository,

    @Autowired
    val menuRepository: MenuRepository,

    @Autowired
    val optionRepository: OptionRepository,
) : OptionService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!

    @Transactional
    override fun createOption(optionCreateRequest: OptionCreateRequest) {
        val newOption = when(optionCreateRequest.type) {
            OptionType.MENU -> {
                val menu = menuRepository.findById(optionCreateRequest.typeId).orElseThrow { menuDoesNotExists() }
                Option(
                    name = optionCreateRequest.name,
                    displayOrder = optionCreateRequest.displayOrder,
                    type = OptionType.MENU,
                    menu = menu
                )
            }
            OptionType.CATEGORY -> {
                val category = categoryRepository.findById(optionCreateRequest.typeId).orElseThrow { categoryDoesNotExists() }
                Option(
                    name = optionCreateRequest.name,
                    displayOrder = optionCreateRequest.displayOrder,
                    type = OptionType.CATEGORY,
                    category = category
                )
            }
        }

        optionCreateRequest.subOptions.values.forEach {
            SubOption(
                name = it.name,
                price = it.price,
                displayOrder = it.displayOrder,
                option = newOption
            )
        }

        optionRepository.findByName(optionCreateRequest.name).ifPresentOrElse(
            { throw optionAlreadyExists() },
            { optionRepository.save(newOption) }
        )
    }

    override fun getByMenuId(optionGetRequest: OptionGetRequest): OptionPageData {
        val menu = menuRepository.findById(optionGetRequest.menuId).orElseThrow { menuDoesNotExists() }
        val categoryIds = categoryRepository.findParentCategoriesRecursiveById(menu.category.id!!).map { it.id!! }.toMutableList()
        val options = optionRepository.findOptionsByCategoryIdInAndMenuId(categoryIds = categoryIds, menuId = menu.id!!)
        val optionDataList = options.map(this::convertToOptionData).toMutableList()

        return OptionPageData(
                paging = Paging(
                        page = 1,
                        total = options.size.toLong(),
                        first = false,
                        last = false,
                        next = false,
                        prev = false,
                ),
                list = optionDataList
        )
    }

    fun convertToOptionData(option: Option): OptionData {
        val subOptions = option.subOptions

        val subOptionDataList = subOptions.map {
            SubOptionData(
                    id = it.id!!,
                    name = it.name,
                    order = it.displayOrder,
                    price = it.price
            )
        }.toMutableList()

        return OptionData(
                id = option.id!!,
                name = option.name,
                order = option.displayOrder,
                type = option.type.name,
                subOptions = subOptionDataList
        )
    }
}

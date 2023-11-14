package com.devh.cafe.api.menu.service

import com.devh.cafe.api.common.paging.Paging
import com.devh.cafe.api.menu.controller.request.MenuCreateRequest
import com.devh.cafe.api.menu.controller.request.MenuDeleteRequest
import com.devh.cafe.api.menu.controller.request.MenuGetRequest
import com.devh.cafe.api.menu.controller.request.MenuGetType
import com.devh.cafe.api.menu.controller.request.MenuUpdateRequest
import com.devh.cafe.api.menu.controller.response.CategorySimpleData
import com.devh.cafe.api.menu.controller.response.MenuData
import com.devh.cafe.api.menu.controller.response.MenuPageData
import com.devh.cafe.api.menu.exception.categoryDoesNotExists
import com.devh.cafe.api.menu.exception.menuAlreadyExists
import com.devh.cafe.api.menu.exception.menuDoesNotExists
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.repository.MenuRepository
import com.devh.cafe.infrastructure.database.entity.Menu
import jakarta.transaction.Transactional
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class MenuServiceImpl(
    @Autowired
    private val categoryRepository: CategoryRepository,

    @Autowired
    private val menuRepository: MenuRepository,
) : MenuService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!

    @Transactional
    override fun create(menuCreateRequests: MutableList<MenuCreateRequest>): MutableList<MenuData> {
        val list = mutableListOf<Menu>()
        menuCreateRequests.forEach {
            val category = categoryRepository.findById(it.categoryId).orElseThrow { categoryDoesNotExists() }
            menuRepository.findByName(it.menuName).ifPresentOrElse(
                { throw menuAlreadyExists() },
                { list.add(Menu(name = it.menuName, price = it.menuPrice, category = category)) }
            )
        }
        return menuRepository.saveAll(list).map(this::convertToMenuData).toMutableList()
    }

    @Transactional
    override fun get(menuGetRequest: MenuGetRequest): MenuPageData {
        val categories = when (menuGetRequest.type) {
            MenuGetType.BY_CATEGORY_ID -> {
                categoryRepository.findSubCategoriesRecursiveById(id = menuGetRequest.categoryId!!)
            }
            MenuGetType.BY_CATEGORY_NAME -> {
                categoryRepository.findSubCategoriesRecursiveByName(name = menuGetRequest.categoryName!!)
            }
        }
        if (categories.isEmpty()) {
            throw categoryDoesNotExists()
        }
        val page = menuRepository.findPageByCategories(categoryIds = categories.map { it.id!! }.toMutableList(),
                                                       pageable = PageRequest.of(
                                                               menuGetRequest.page - 1, menuGetRequest.size))

        return MenuPageData(
            paging = Paging(
                page = page.pageable.pageNumber + 1,
                total = page.totalElements,
                first = page.isFirst,
                last = page.isLast,
                next = page.hasNext(),
                prev = page.hasPrevious()
            ),
            list = page.content.map(this::convertToMenuData).toMutableList()
        )
    }

    @Transactional
    override fun update(menuUpdateRequest: MenuUpdateRequest): MenuData {
        val menu = menuRepository.findById(menuUpdateRequest.id).orElseThrow { menuDoesNotExists() }
        val menuName = menu.name
        val menuPrice = menu.price
        val menuAvailable = menu.available
        val categoryId = menu.category.id

        val newCategoryId = menuUpdateRequest.categoryId
        val newName = menuUpdateRequest.name
        val newPrice = menuUpdateRequest.price
        val newAvailable = menuUpdateRequest.available

        if (categoryId != newCategoryId) {
            val newCategory = categoryRepository.findById(newCategoryId).orElseThrow { categoryDoesNotExists() }
            menu.changeCategory(newCategory)
        }

        if (menuName != newName) {
            menuRepository.findByName(newName).ifPresent { throw menuAlreadyExists() }
            menu.changeName(newName)
        }

        if (menuPrice != newPrice) {
            menu.changePrice(newPrice)
        }

        if (menuAvailable != newAvailable) {
            menu.changeAvailable(newAvailable)
        }

        return convertToMenuData(menu)
    }

    @Transactional
    override fun delete(menuDeleteRequest: MenuDeleteRequest) {
        menuRepository.deleteAllById(menuDeleteRequest.ids)
    }

    private fun convertToMenuData(menu: Menu): MenuData {
        return MenuData(
            id = menu.id!!,
            name = menu.name,
            price = menu.price,
            available = menu.available,
            category = CategorySimpleData(id = menu.category.id!!, name = menu.category.name),
        )
    }
}

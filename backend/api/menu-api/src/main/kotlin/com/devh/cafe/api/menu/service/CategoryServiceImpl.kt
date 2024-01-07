package com.devh.cafe.api.menu.service

import com.devh.cafe.api.common.paging.Paging
import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryDeleteRequest
import com.devh.cafe.api.menu.controller.request.CategoryGetRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.controller.response.CategoryData
import com.devh.cafe.api.menu.controller.response.CategoryPageData
import com.devh.cafe.api.menu.controller.response.CategorySimpleData
import com.devh.cafe.api.menu.exception.categoryAlreadyExists
import com.devh.cafe.api.menu.exception.categoryDoesNotExists
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.infrastructure.database.entity.Category
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    @Autowired
    private val categoryRepository: CategoryRepository,
) : CategoryService {

    @Transactional
    override fun create(categoryCreateRequest: CategoryCreateRequest): CategoryData {
        categoryRepository.findByName(categoryCreateRequest.name).ifPresent { throw categoryAlreadyExists() }
        val parent = if (categoryCreateRequest.parentId != null) {
            categoryRepository.findById(categoryCreateRequest.parentId).orElseThrow { categoryDoesNotExists() }
        } else null
        return convertToCategoryData(categoryRepository.save(Category(name = categoryCreateRequest.name, parent = parent)))
    }

    @Transactional
    override fun getByName(name: String): CategoryData {
        return convertToCategoryData(categoryRepository.findByName(name).orElseThrow { throw categoryDoesNotExists() })
    }

    @Transactional
    override fun getByNames(names: MutableList<String>): MutableList<CategoryData> {
        val categories = categoryRepository.findAllByNameIn(names)
        if (categories.isEmpty()) { throw categoryDoesNotExists() }
        return categories.map(this::convertToCategoryData).toMutableList()
    }

    @Transactional
    override fun getById(id: Long): CategoryData {
        return convertToCategoryData(categoryRepository.findById(id).orElseThrow { throw categoryDoesNotExists() })
    }

    @Transactional
    override fun getByIds(ids: MutableList<Long>): MutableList<CategoryData> {
        val categories = categoryRepository.findAllById(ids)
        if (categories.isEmpty()) { throw categoryDoesNotExists() }
        return categories.map(this::convertToCategoryData).toMutableList()
    }

    @Transactional
    override fun get(categoryGetRequest: CategoryGetRequest): CategoryPageData {
        val page = categoryRepository.findAll(PageRequest.of(categoryGetRequest.page - 1, categoryGetRequest.size))
        return CategoryPageData(
            paging = Paging(
                page = page.pageable.pageNumber + 1,
                total = page.totalElements,
                first = page.isFirst,
                last = page.isLast,
                next = page.hasNext(),
                prev = page.hasPrevious()
            ),
            list = page.content.map(this::convertToCategoryData).toMutableList()
        )
    }

    override fun getSubCategoriesByParentId(categoryGetRequest: CategoryGetRequest): CategoryPageData {

        val parentCategory = categoryRepository.findById(categoryGetRequest.parentId!!).orElseThrow { throw categoryDoesNotExists() }

        val pageRequest = PageRequest.of(categoryGetRequest.page - 1, categoryGetRequest.size)
        val subCategoriesPage = categoryRepository.findAllByParent(parentCategory, pageRequest)

        return CategoryPageData(
            paging = Paging(
                page = subCategoriesPage.pageable.pageNumber + 1,
                total = subCategoriesPage.totalElements,
                first = subCategoriesPage.isFirst,
                last = subCategoriesPage.isLast,
                next = subCategoriesPage.hasNext(),
                prev = subCategoriesPage.hasPrevious()
            ),
            list = subCategoriesPage.content.map(this::convertToCategoryData).toMutableList()
        )
    }

    override fun getSubCategoryNamesRecursiveByName(name: String): MutableList<CategorySimpleData> {
        return categoryRepository.findSubCategoriesRecursiveByName(name)
            .map { CategorySimpleData(id = it.id!!, name = it.name) }
            .toMutableList()
    }

    override fun getSubCategoryNamesRecursiveById(id: Long): MutableList<CategorySimpleData> {
        return categoryRepository.findSubCategoriesRecursiveById(id)
            .map { CategorySimpleData(id = it.id!!, name = it.name) }
            .toMutableList()
    }

    override fun getParentCategoryNamesRecursiveByName(name: String): MutableList<CategorySimpleData> {
        return categoryRepository.findParentCategoriesRecursiveByName(name)
            .map { CategorySimpleData(id = it.id!!, name = it.name) }
            .toMutableList()
    }

    override fun getParentCategoryNamesRecursiveById(id: Long): MutableList<CategorySimpleData> {
        return categoryRepository.findParentCategoriesRecursiveById(id)
            .map { CategorySimpleData(id = it.id!!, name = it.name) }
            .toMutableList()
    }

    @Transactional
    override fun update(categoryUpdateRequest: CategoryUpdateRequest): CategoryData {
        val category = categoryRepository.findById(categoryUpdateRequest.id).orElseThrow { categoryDoesNotExists() }
        val name = category.name

        val newName = categoryUpdateRequest.name

        if (name != newName) {
            category.changeName(newName)
        }

        return convertToCategoryData(category)
    }

    @Transactional
    override fun delete(categoryDeleteRequest: CategoryDeleteRequest) {
        categoryRepository.deleteAllById(categoryDeleteRequest.ids)
    }

    private fun convertToCategoryData(category: Category): CategoryData {
        val parentData = if (category.hasParent()) {
            CategorySimpleData(
                id = category.parent!!.id!!,
                name = category.parent!!.name,
            )
        } else null
        val subCategoriesData = if (category.hasSubCategories()) {
            category.subCategories.map {
                CategorySimpleData(
                    id = it.id!!,
                    name = it.name,
                )
            }.toMutableList()
        } else mutableListOf()
        return CategoryData(
            id = category.id!!,
            name = category.name,
            parent = parentData,
            subCategories = subCategoriesData
        )
    }
}

package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryDeleteRequest
import com.devh.cafe.api.menu.controller.request.CategoryGetRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.controller.response.CategoryData
import com.devh.cafe.api.menu.controller.response.CategoryPageData
import com.devh.cafe.api.menu.controller.response.CategorySimpleData

interface CategoryService {
    fun create(categoryCreateRequest: CategoryCreateRequest): CategoryData
    fun getByName(name: String): CategoryData
    fun getByNames(names: MutableList<String>): MutableList<CategoryData>
    fun getById(id: Long): CategoryData
    fun getByIds(ids: MutableList<Long>): MutableList<CategoryData>
    fun get(categoryGetRequest: CategoryGetRequest): CategoryPageData
    fun getSubCategoriesByParentId(categoryGetRequest: CategoryGetRequest): CategoryPageData
    fun getSubCategoryNamesRecursiveByName(name: String): MutableList<CategorySimpleData>
    fun getSubCategoryNamesRecursiveById(id: Long): MutableList<CategorySimpleData>
    fun update(categoryUpdateRequest: CategoryUpdateRequest): CategoryData
    fun delete(categoryDeleteRequest: CategoryDeleteRequest)
}

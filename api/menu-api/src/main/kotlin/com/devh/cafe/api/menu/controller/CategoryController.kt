package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.paging.DEFAULT_SIZE
import com.devh.cafe.api.common.response.Message
import com.devh.cafe.api.common.response.MessageResponse
import com.devh.cafe.api.common.response.PageResponse
import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryDeleteRequest
import com.devh.cafe.api.menu.controller.request.CategoryGetRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.controller.response.CategoryData
import com.devh.cafe.api.menu.service.CategoryService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/category")
@Validated
class CategoryController(
    @Autowired
    val categoryService: CategoryService,
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!

    @PutMapping
    fun putCategory(@Valid @RequestBody categoryCreateRequest: CategoryCreateRequest): MessageResponse {
        log.debug("category create request: {}", categoryCreateRequest)
        categoryService.create(categoryCreateRequest)
        return MessageResponse(message = Message.SUCCESS)
    }

    @GetMapping
    fun getCategory(
        @Min(1) @RequestParam(defaultValue = "1") page: Int,
        @Min(1) @RequestParam(defaultValue = "$DEFAULT_SIZE") size: Int,
    ): PageResponse<CategoryData> {
        val categoryGetRequest = CategoryGetRequest(
            page = page,
            size = size,
        )
        log.debug("category get request")
        val categoryPageData = categoryService.get(categoryGetRequest)
        return PageResponse(
            paging = categoryPageData.paging,
            list = categoryPageData.list
        )
    }

    @PostMapping
    fun updateCategory(@Valid @RequestBody categoryUpdateRequest: CategoryUpdateRequest): MessageResponse {
        log.debug("category update request: {}", categoryUpdateRequest)
        categoryService.update(categoryUpdateRequest)
        return MessageResponse(message = Message.SUCCESS)
    }

    @DeleteMapping("/{ids}")
    fun deleteCategory(@Size(max = 10) @Min(1) @PathVariable ids: MutableList<Long>): MessageResponse {
        log.debug("category delete request: {}", ids)
        categoryService.delete(CategoryDeleteRequest(ids = ids))
        return MessageResponse(message = Message.SUCCESS)
    }
}

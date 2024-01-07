package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.paging.DEFAULT_SIZE
import com.devh.cafe.api.common.response.Message
import com.devh.cafe.api.common.response.MessageResponse
import com.devh.cafe.api.common.response.PageResponse
import com.devh.cafe.api.menu.controller.request.MenuCreateRequest
import com.devh.cafe.api.menu.controller.request.MenuDeleteRequest
import com.devh.cafe.api.menu.controller.request.MenuGetRequest
import com.devh.cafe.api.menu.controller.request.MenuGetType
import com.devh.cafe.api.menu.controller.request.MenuUpdateRequest
import com.devh.cafe.api.menu.controller.response.MenuData
import com.devh.cafe.api.menu.service.MenuService
import jakarta.validation.Valid
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
@RequestMapping("/v1/menu")
@Validated
class MenuController(
    @Autowired
    val menuService: MenuService,
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!

    @PutMapping
    fun putMenu(@Valid @Size(min = 1, max = 15) @RequestBody menuCreateRequests: MutableList<MenuCreateRequest>): MessageResponse {
        log.debug("menu create request: {}", menuCreateRequests)
        menuService.create(menuCreateRequests)
        return MessageResponse(message = Message.SUCCESS)
    }

    @GetMapping("/category/id/{categoryId}")
    fun getMenu(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "$DEFAULT_SIZE") size: Int,
    ): PageResponse<MenuData> {
        log.debug("menu get by cid request: {}", categoryId)
        val menuGetRequest = MenuGetRequest(
            type = MenuGetType.BY_CATEGORY_ID,
            page = page,
            size = size,
            categoryId = categoryId,
        )
        val menuPageData = menuService.get(menuGetRequest)
        return PageResponse(
            paging = menuPageData.paging,
            list = menuPageData.list
        )
    }

    @GetMapping("/category/name/{categoryName}")
    fun getMenu(
        @PathVariable categoryName: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "$DEFAULT_SIZE") size: Int,
    ): PageResponse<MenuData> {
        log.debug("menu get by cname request: {}", categoryName)
        val menuGetRequest = MenuGetRequest(
            type = MenuGetType.BY_CATEGORY_NAME,
            page = page,
            size = size,
            categoryName = categoryName,
        )
        val menuPageData = menuService.get(menuGetRequest)
        return PageResponse(
            paging = menuPageData.paging,
            list = menuPageData.list
        )
    }

    @PostMapping
    fun updateMenu(@Valid @RequestBody menuUpdateRequest: MenuUpdateRequest): MessageResponse {
        log.debug("menu update request: {}", menuUpdateRequest)
        menuService.update(menuUpdateRequest)
        return MessageResponse(message = Message.SUCCESS)
    }

    @DeleteMapping("/{ids}")
    fun deleteMenu(@Size(max = 10) @PathVariable ids: MutableList<Long>): MessageResponse {
        log.debug("menu delete request: {}", ids)
        menuService.delete(MenuDeleteRequest(ids = ids))
        return MessageResponse(message = Message.SUCCESS)
    }
}

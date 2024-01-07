package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.MenuCreateRequest
import com.devh.cafe.api.menu.controller.request.MenuDeleteRequest
import com.devh.cafe.api.menu.controller.request.MenuGetRequest
import com.devh.cafe.api.menu.controller.request.MenuUpdateRequest
import com.devh.cafe.api.menu.controller.response.MenuData
import com.devh.cafe.api.menu.controller.response.MenuPageData

interface MenuService {
    fun create(menuCreateRequests: MutableList<MenuCreateRequest>): MutableList<MenuData>
    fun get(menuGetRequest: MenuGetRequest): MenuPageData
    fun update(menuUpdateRequest: MenuUpdateRequest): MenuData
    fun delete(menuDeleteRequest: MenuDeleteRequest)
}

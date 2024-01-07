package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.paging.Paging
import com.devh.cafe.api.menu.controller.request.OptionGetRequest
import com.devh.cafe.api.menu.controller.response.OptionData
import com.devh.cafe.api.menu.controller.response.OptionPageData
import com.devh.cafe.api.menu.controller.response.SubOptionData
import com.devh.cafe.api.menu.service.OptionService
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionsAmericano
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class OptionControllerUnitTest {
    @InjectMocks
    lateinit var optionController: OptionController

    @Mock
    lateinit var optionService: OptionService

    @Test
    fun 메뉴_아이디가_주어질_때_옵션_조회에_성공한다() {
        // given
        val givenMenu = fixtureMenuAmericano
        val givenOptions = fixtureOptionsAmericano
        val givenRequest = OptionGetRequest(
            menuId = givenMenu.id!!
        )
        Mockito.`when`(
            optionService.getByMenuId(givenRequest)
        ).thenReturn(
            OptionPageData(
                paging = Paging(
                    page = 1,
                    total = givenOptions.size.toLong(),
                    first = true,
                    last = true,
                    next = false,
                    prev = false,
                ),
                list = givenOptions.map {
                    OptionData(
                        id = it.id!!,
                        name = it.name,
                        order = it.displayOrder,
                        type = it.type.toString(),
                        subOptions = it.subOptions.map {subIt ->
                            SubOptionData(
                                id = subIt.id!!,
                                name = subIt.name,
                                order = subIt.displayOrder,
                                price = subIt.price,
                            )
                        }.toMutableList(),
                    )
                }.toMutableList()
            )
        )
        // when
        val pageResponse = optionController.getByMenuId(givenMenu.id!!)
        // then
        assertAll(
            { Assertions.assertEquals(200, pageResponse.status) },
            { Assertions.assertEquals(givenOptions.size, pageResponse.list.size) },
        )
    }
}
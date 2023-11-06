package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.menu.controller.configuration.ControllerTest
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionsAmericano
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ControllerTest
class OptionControllerIntegrationTest(
    @Autowired
    val mockMvc: MockMvc,

) {

    private val urlPrefix: String = "/v1/option"

    @Test
    fun 메뉴_아이디가_주어질_때_옵션_조회에_성공한다() {
        // given
        val givenMenu = fixtureMenuAmericano
        val givenOptions = fixtureOptionsAmericano
        // when & then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$urlPrefix/menu/id/${givenMenu.id!!}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
            .andDo(MockMvcResultHandlers.print())
            .andReturn()

    }
}
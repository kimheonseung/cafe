package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.response.ErrorType
import com.devh.cafe.api.common.response.Message
import com.devh.cafe.api.menu.controller.configuration.ControllerTest
import com.devh.cafe.api.menu.controller.request.MenuCreateRequest
import com.devh.cafe.api.menu.controller.request.MenuUpdateRequest
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.repository.MenuRepository
import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.entity.Menu
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ControllerTest
class MenuControllerIntegrationTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val categoryRepository: CategoryRepository,

    @Autowired
    val menuRepository: MenuRepository,

    @Autowired
    val objectMapper: ObjectMapper,
) {
    private val urlPrefix: String = "/v1/menu"
    lateinit var defaultCategory: Category
    lateinit var defaultMenu1: Menu
    lateinit var defaultMenu2: Menu

    @BeforeEach
    fun 기본_카테고리_1개와_기본_메뉴_2개를_세팅한다() {
        defaultCategory = categoryRepository.save(Category(name = "기본 카테고리"))
        defaultMenu1 = menuRepository.save(
            Menu(name = "기본 메뉴1", price = 1500L, available = true, category = defaultCategory))
        defaultMenu2 = menuRepository.save(
            Menu(name = "기본 메뉴2", price = 2500L, available = true, category = defaultCategory))
    }

    @Test
    fun 메뉴_저장_요청_객체_1개가_주어질_때_저장에_성공한다() {
        // given
        val givenName = "메뉴"
        val givenPrice = 1000L
        val body = objectMapper.writeValueAsString(
            mutableListOf(
                MenuCreateRequest(categoryId = defaultCategory.id!!, menuName = givenName, menuPrice = givenPrice)
            )
        )
        // when & then
        mockMvc.perform(
            put(urlPrefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(Message.SUCCESS))
            .andExpect(jsonPath("$.status").value(200))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 이미_존재하는_메뉴_1개가_포함된_메뉴_저장_요청이_주어질_때_저장에_실패한다() {
        // given
        val givenPrice = 1000L
        val body = objectMapper.writeValueAsString(
            mutableListOf(
                MenuCreateRequest(categoryId = defaultCategory.id!!, menuName = defaultMenu1.name, menuPrice = givenPrice)
            )
        )
        // when & then
        mockMvc.perform(
            put(urlPrefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(ErrorType.MENU_ERROR.number))
            .andExpect(jsonPath("$.message").value(ErrorType.MENU_ERROR.message))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 카테고리_id가_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        // when & then
        mockMvc.perform(
            get("$urlPrefix/category/id/${defaultCategory.id}?page=$givenPage&size=$givenSize")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.paging.page").value(givenPage))
            .andExpect(jsonPath("$.status").value(200))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 존재하지_않는_카테고리_id가_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        val givenUnknownCategoryId = -1L
        // when & then
        mockMvc.perform(
            get("$urlPrefix/category/id/$givenUnknownCategoryId?page=$givenPage&size=$givenSize")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(ErrorType.CATEGORY_ERROR.number))
            .andExpect(jsonPath("$.message").value(ErrorType.CATEGORY_ERROR.message))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 카테고리_이름이_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        // when & then
        mockMvc.perform(
            get("$urlPrefix/category/name/${defaultCategory.name}?page=$givenPage&size=$givenSize")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.paging.page").value(givenPage))
            .andExpect(jsonPath("$.status").value(200))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 존재하지_않는_카테고리_이름이_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        val givenUnknownCategoryName = "존재하지 않는 카테고리"
        // when & then
        mockMvc.perform(
            get("$urlPrefix/category/name/$givenUnknownCategoryName?page=$givenPage&size=$givenSize")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(ErrorType.CATEGORY_ERROR.number))
            .andExpect(jsonPath("$.message").value(ErrorType.CATEGORY_ERROR.message))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 메뉴의_최신_정보가_주어질_때_변경에_성공한다() {
        // given
        val givenId = defaultMenu1.id!!
        val givenName = "수정이름"
        val body = objectMapper.writeValueAsString(
            MenuUpdateRequest(
                id = givenId,
                name = givenName,
                price = defaultMenu1.price,
                available = defaultMenu1.available,
                categoryId = defaultCategory.id!!,
            )
        )
        // when & then
        mockMvc.perform(
            post(urlPrefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(Message.SUCCESS))
            .andExpect(jsonPath("$.status").value(200))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 메뉴의_최신_정보가_존재하지_않는_카테고리_id를_포함할_때_변경에_실패한다() {
        // given
        val givenId = defaultMenu1.id!!
        val givenName = "수정이름"
        val givenUnknownCategoryId = -1L
        val body = objectMapper.writeValueAsString(
            MenuUpdateRequest(
                id = givenId,
                name = givenName,
                price = defaultMenu1.price,
                available = defaultMenu1.available,
                categoryId = givenUnknownCategoryId,
            )
        )
        // when & then
        mockMvc.perform(
            post(urlPrefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(ErrorType.CATEGORY_ERROR.number))
            .andExpect(jsonPath("$.message").value(ErrorType.CATEGORY_ERROR.message))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 메뉴의_최신_정보가_이미_존재하는_메뉴_이름을_포함할_때_변경에_실패한다() {
        // given
        val givenId = defaultMenu1.id!!
        val givenName = "기본 메뉴2"
        val givenUnknownCategoryId = -1L
        val body = objectMapper.writeValueAsString(
            MenuUpdateRequest(
                id = givenId,
                name = givenName,
                price = defaultMenu1.price,
                available = defaultMenu1.available,
                categoryId = givenUnknownCategoryId,
            )
        )
        // when & then
        mockMvc.perform(
            post(urlPrefix)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(ErrorType.CATEGORY_ERROR.number))
            .andExpect(jsonPath("$.message").value(ErrorType.CATEGORY_ERROR.message))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 여러개의_메뉴_id가_주어질_때_삭제에_성공한다() {
        // given
        val givenId = defaultMenu1.id!!
        // when & then
        mockMvc.perform(
            delete("$urlPrefix/$givenId")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value(Message.SUCCESS))
            .andExpect(jsonPath("$.status").value(200))
            .andDo(print())
            .andReturn()
    }
}

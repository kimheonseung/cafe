package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.response.ErrorType
import com.devh.cafe.api.common.response.Message
import com.devh.cafe.api.menu.controller.configuration.ControllerTest
import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.infrastructure.database.entity.Category
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
class CategoryControllerIntegrationTest(
    @Autowired
    val mockMvc: MockMvc,

    @Autowired
    val categoryRepository: CategoryRepository,

    @Autowired
    val objectMapper: ObjectMapper,
) {
    private val urlPrefix: String = "/v1/category"
    lateinit var defaultCategory: Category

    @BeforeEach
    fun 기본_카테고리_1개를_세팅한다() {
        defaultCategory = categoryRepository.save(Category(name = "기본 카테고리"))
    }

    @Test
    fun 카테고리_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenCategoryName = "카테고리"
        val body = objectMapper.writeValueAsString(CategoryCreateRequest(name = givenCategoryName))
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
    fun 이미_등록된_카테고리_이름으로_저장에_실패한다() {
        // given
        val givenCategoryName = defaultCategory.name
        val body = objectMapper.writeValueAsString(CategoryCreateRequest(name = givenCategoryName))
        // when & then
        mockMvc.perform(
            put(urlPrefix)
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
    fun 페이지_정보가_주어질_때_페이지_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        // when & then
        mockMvc.perform(
            get("$urlPrefix?page=$givenPage&size=$givenSize")
        )
            .andExpect(status().isOk)
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.paging.page").value(givenPage))
            .andExpect(jsonPath("$.status").value(200))
            .andDo(print())
            .andReturn()
    }

    @Test
    fun 변경할_카테고리_이름이_주어질_때_변경에_성공한다() {
        // given
        val givenId = defaultCategory.id!!
        val givenName = "새로운 카테고리"
        val body = objectMapper.writeValueAsString(CategoryUpdateRequest(id = givenId, name = givenName))
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
    fun 한개_이상의_카테고리_id가_주어질_때_삭제에_성공한다() {
        // given
        val givenId = defaultCategory.id!!
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

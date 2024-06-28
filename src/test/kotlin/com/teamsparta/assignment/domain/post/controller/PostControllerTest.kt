package com.teamsparta.assignment.domain.post.controller

import com.teamsparta.assignment.domain.post.dto.RetrievePostResponse
import com.teamsparta.assignment.domain.post.service.PostService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus



class PostControllerTest : BehaviorSpec({
    val savedPostId = 1L
    val notSavedPostId = 10L
    val postService = mockk<PostService>()

    val postController = PostController(postService)

    every { postService.findById(savedPostId) } returns RetrievePostResponse(
        id = savedPostId,
        title = "title",
        description = "description",
        comment = emptyList()
    )

    every { postService.findById(notSavedPostId) } returns null

    Given("저장된 게시물 ID가 주어졌을 경우") {
        val targetPostId = savedPostId
        When("해당 ID로 게시물을 조회하면") {
            val result = postController.getPostById(targetPostId)
            Then("상태 코드는 OK") {
                result.statusCode shouldBe HttpStatus.OK
                result.body?.id shouldBe savedPostId
            }
        }
    }
    Given("저장되지 않은 게시물 ID가 주어졌을 경우") {
        val targetPostId = notSavedPostId
        When("해당 ID로 게시물을 조회하면") {
            val result = postController.getPostById(targetPostId)
            Then("상태 코드는 NOT FOUND") {
                result.statusCode shouldBe HttpStatus.NOT_FOUND
                result.body shouldBe null
            }
        }
    }
})

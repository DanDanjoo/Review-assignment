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

    Given("saved post id"){
        val targetPostId = savedPostId
        When("findPostId"){
            val result = postController.getPostById(targetPostId)
            Then("status code should be ok") {
                result.statusCode shouldBe HttpStatus.OK
                result.body?.id shouldBe savedPostId
            }
        }
    }
    Given("not saved post id"){
        val targetPostId = notSavedPostId
        When("findPostId"){
            val result = postController.getPostById(targetPostId)
            Then("status code should be not found") {
                result.statusCode shouldBe HttpStatus.NOT_FOUND
                result.body shouldBe null
            }
        }
    }
})

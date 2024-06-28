package com.teamsparta.assignment.domain.post.controller

import com.teamsparta.assignment.domain.post.dto.CreatePostRequest
import com.teamsparta.assignment.domain.post.dto.PostResponse
import com.teamsparta.assignment.domain.post.dto.RetrievePostResponse
import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import com.teamsparta.assignment.domain.post.service.PostService
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication


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

    Given("게시물 생성 요청이 들어왔을 경우") {
        val request = CreatePostRequest(title = "new title", description = "new description")
        val userPrincipal = mockk<UserPrincipal>()
        val authentication = mockk<Authentication> {
            every { principal } returns userPrincipal
        }

        every { postService.create(request, userPrincipal)} returns PostResponse(
            id = savedPostId,
            title = "new title",
            description = "new description",
        )

        When("createPost 메서드를 호출하면") {
            val result = postController.createPost(request, authentication)
            Then("상태 코드는 CREATE, 생성된 게시물의 ID는 반환되어야 한다.") {
                result.statusCode shouldBe HttpStatus.CREATED
                result.body?.id shouldBe savedPostId
            }
        }
    }

    Given("게시물 수정 요청이 들어왔을 경우") {
        val request = UpdatePostRequest(title = "updated title", description = "updated description")
        val userPrincipal = mockk<UserPrincipal>()
        val authentication = mockk<Authentication> {
            every { principal } returns userPrincipal
        }

        every { postService.update(savedPostId, request, userPrincipal.to()) } returns PostResponse(
            id = savedPostId,
            title = request.title,
            description = request.description
        )

        When("updatePost 메서드를 호출하면") {
            val result = postController.updatePost(savedPostId, request, authentication)
            Then("상태 코드는 OK, 수정된 게시물의 ID는 반환되어야 한다.") {
                result.statusCode shouldBe HttpStatus.OK
                result.body?.id shouldBe savedPostId
            }
        }
    }

    Given("게시물 삭제 요청이 들어왔을 경우") {
        val userPrincipal = mockk<UserPrincipal>()
        val authentication = mockk<Authentication> {
            every { principal } returns userPrincipal
        }

        every { postService.delete(savedPostId, userPrincipal) } returns Unit

        When("deletePost 메서드를 호출하면") {
            val result = postController.deletePost(savedPostId, authentication)
            Then("상태 코드는 NO_CONTENT, 바디는 NULL")
            result.statusCode shouldBe HttpStatus.NO_CONTENT
            result.body shouldBe null
        }
    }
})

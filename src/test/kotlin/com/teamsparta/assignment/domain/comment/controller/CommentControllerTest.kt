package com.teamsparta.assignment.domain.comment.controller

import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.post.comment.controller.CommentController
import com.teamsparta.assignment.domain.post.comment.dto.CommentResponse
import com.teamsparta.assignment.domain.post.comment.dto.CreateCommentRequest
import com.teamsparta.assignment.domain.post.comment.dto.UpdateCommentRequest
import com.teamsparta.assignment.domain.post.comment.service.CommentService
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication


class CommentControllerTest : BehaviorSpec({
    val commentService = mockk<CommentService>()
    val commentController = CommentController(commentService)
    val userPrincipal = UserPrincipal(1L, "my nickname")
    val authentication = mockk<Authentication>()

    every { authentication.principal } returns userPrincipal

    Given("댓글 생성 요청이 들어왔을 경우") {
        val request = CreateCommentRequest("my comment")
        val postId = 1L
        val response = CommentResponse("my comment")

        every { commentService.create(postId, request, userPrincipal) } returns response

        When("createComment 메서드를 호출하면") {
            val result = commentController.createComment(request, postId, authentication)
            Then("상태 코드는 CREATED, 댓글 반환") {
                result.statusCode shouldBe HttpStatus.CREATED
                result.body shouldBe response
            }
        }
    }

    Given("댓글 수정 요청이 들어왔을 경우") {
        val request = UpdateCommentRequest("updated comment")
        val postId = 1L
        val commentId = 1L
        val response = CommentResponse("updated comment")
        val member = Member(1L, "my nickname", "")

        every { commentService.update(postId, commentId, request, member) } returns response

        When("updateComment 메서드를 호출할 시") {
            val result = commentController.updateComment(request, postId, commentId, authentication)
            Then("상태 코드는 OK, 수정된 댓글을 반환") {
                result.statusCode shouldBe HttpStatus.OK
                result.body shouldBe response
            }
        }
    }

    Given("댓글 삭제 요청이 들어왔을 경우") {
        val postId = 1L
        val commentId = 1L

        every { commentService.delete(postId, commentId, userPrincipal) } returns Unit

        When("deleteComment 메서드를 호출할 시") {
            val result = commentController.deleteComment(postId, commentId, authentication)
            Then("상태 코드는 NO_CONTENT, body는 null") {
                result.statusCode shouldBe HttpStatus.NO_CONTENT
                result.body shouldBe null
            }
        }
    }
})
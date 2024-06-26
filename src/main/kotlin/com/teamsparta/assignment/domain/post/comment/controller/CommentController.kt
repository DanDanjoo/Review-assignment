package com.teamsparta.assignment.domain.post.comment.controller

import com.teamsparta.assignment.domain.post.comment.dto.CommentResponse
import com.teamsparta.assignment.domain.post.comment.dto.CreateCommentRequest
import com.teamsparta.assignment.domain.post.comment.dto.UpdateCommentRequest
import com.teamsparta.assignment.domain.post.comment.service.CommentService
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/posts/{postId}/comments")
class CommentController (
    private val commentService: CommentService
){

    @PostMapping
    @Operation(summary = "Comment 생성", description = "댓글을 생성합니다.")
    fun createComment(
        @RequestBody request: CreateCommentRequest,
        @PathVariable postId: Long,
        authentication: Authentication
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(commentService.create(postId, request, authentication.principal as UserPrincipal))
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Comment 수정", description = "댓글을 수정합니다.")
    fun updateComment(
        @RequestBody request: UpdateCommentRequest,
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        authentication: Authentication
    ): ResponseEntity<CommentResponse> {

        val userPrincipal = authentication.principal as UserPrincipal

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(commentService.update(postId, commentId, request, userPrincipal.to()))
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Comment 삭제", description = "댓글을 삭제합니다.")
    fun deleteComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long,
        authentication: Authentication
    ): ResponseEntity<Unit> {
        commentService.delete(postId, commentId, authentication.principal as UserPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)
    }
}
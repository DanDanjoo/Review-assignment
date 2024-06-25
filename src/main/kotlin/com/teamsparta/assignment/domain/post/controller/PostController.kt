package com.teamsparta.assignment.domain.post.controller

import com.teamsparta.assignment.domain.post.dto.PostResponse
import com.teamsparta.assignment.domain.post.dto.CreatePostRequest
import com.teamsparta.assignment.domain.post.dto.RetrievePostResponse
import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import com.teamsparta.assignment.domain.post.service.PostService
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping
    @Operation(summary = "Post 모두 조회", description = "게시글을 모두 조회합니다.")
    fun getPostsList(): ResponseEntity<List<PostResponse>> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.findAll())
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Post Id로 조회", description = "게시글을 조회합니다.")
    fun getPostById(@PathVariable postId: Long): ResponseEntity<RetrievePostResponse> {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.findById(postId))
    }

    @PostMapping
    @Operation(summary = "Post 생성", description = "게시글을 생성합니다.")
    fun createPost(
        @RequestBody request: CreatePostRequest,
        authentication: Authentication
    ): ResponseEntity<PostResponse> {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postService.create(request, authentication.principal as UserPrincipal))

    }

    @PutMapping("/{postId}")
    @Operation(summary = "Post 수정", description = "게시글을 수정합니다.")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody request : UpdatePostRequest,
        authentication: Authentication
    ): ResponseEntity<PostResponse> {

       val userPrincipal = authentication.principal as UserPrincipal

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(postService.update(postId, request,userPrincipal.to()))

    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Post 삭제", description = "게시글을 삭제합니다.")
    fun deletePost(
        @PathVariable postId: Long,
        authentication: Authentication
    ): ResponseEntity<Unit> {
        postService.delete(postId, authentication.principal as UserPrincipal)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null)

    }
}
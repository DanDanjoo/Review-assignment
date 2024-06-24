package com.teamsparta.assignment.domain.post.controller

import com.teamsparta.assignment.domain.post.service.PostService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/posts")
class PostController(
    private val postService: PostService
) {

    @GetMapping
    @Operation(summary = "Post 모두 조회", description = "게시글을 모두 조회합니다.")
    fun getPostsList() {
        //TODO
    }

    @GetMapping
    @Operation(summary = "Post Id로 조회", description = "게시글을 조회합니다.")
    fun getPostById() {
        //TODO
    }

    @PostMapping
    @Operation(summary = "Post 생성", description = "게시글을 생성합니다.")
    fun createPost() {
        //TODO
    }

    @PutMapping
    @Operation(summary = "Post 수정", description = "게시글을 수정합니다.")
    fun updatePost() {
        //TODO
    }

    @DeleteMapping
    @Operation(summary = "Post 삭제", description = "게시글을 삭제합니다.")
    fun deletePost() {
        //TODO
    }
}
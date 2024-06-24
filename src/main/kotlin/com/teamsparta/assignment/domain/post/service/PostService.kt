package com.teamsparta.assignment.domain.post.service

import com.teamsparta.assignment.domain.exception.ModelNotFoundException
import com.teamsparta.assignment.domain.post.dto.CreatePostRequest
import com.teamsparta.assignment.domain.post.dto.PostResponse
import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import com.teamsparta.assignment.domain.post.model.Post
import com.teamsparta.assignment.domain.post.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PostService (
    private val postRepository : PostRepository
) {

    fun findAll() : List<PostResponse> {

        val foundAllPosts = postRepository.findAllByOrderByCreatedAtDesc()

        return foundAllPosts.map { PostResponse.from(it)}

    }

    fun findById(postId: Long): PostResponse {

       val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

       return PostResponse.from(foundPost)

    }

    fun create(request: CreatePostRequest) : PostResponse {

        val post = Post(request.nickname, request.title, request.description)

        val createdPost = postRepository.save(post)

        return PostResponse.from(createdPost)

    }

    @Transactional
    fun update(postId: Long, request: UpdatePostRequest): PostResponse {

        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        foundPost.updatePostField(request)

        return PostResponse.from(foundPost)
    }

    fun delete(postId: Long) {

        postRepository.deleteById(postId)

    }
}
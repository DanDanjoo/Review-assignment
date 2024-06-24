package com.teamsparta.assignment.domain.post.comment.service

import com.teamsparta.assignment.domain.exception.ModelNotFoundException
import com.teamsparta.assignment.domain.member.repository.MemberRepository
import com.teamsparta.assignment.domain.post.comment.dto.CommentResponse
import com.teamsparta.assignment.domain.post.comment.dto.CreateCommentRequest
import com.teamsparta.assignment.domain.post.comment.model.Comment
import com.teamsparta.assignment.domain.post.comment.repository.CommentRepository
import com.teamsparta.assignment.domain.post.repository.PostRepository
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CommentService (
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun create(postId: Long, request: CreateCommentRequest, userPrincipal: UserPrincipal): CommentResponse {

        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        foundPost.checkAuthorization(userPrincipal.to())

        val members = memberRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("User", userPrincipal.id)

        val comment = Comment(
            request.content,
            foundPost,
            members
       )
        val savedComment = commentRepository.save(comment)

        return CommentResponse.from(savedComment)
    }
}
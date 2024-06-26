package com.teamsparta.assignment.domain.post.service

import com.teamsparta.assignment.domain.exception.ModelNotFoundException
import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.member.repository.MemberRepository
import com.teamsparta.assignment.domain.post.dto.CreatePostRequest
import com.teamsparta.assignment.domain.post.dto.PostResponse
import com.teamsparta.assignment.domain.post.dto.RetrievePostResponse
import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import com.teamsparta.assignment.domain.post.model.Post
import com.teamsparta.assignment.domain.post.repository.PostRepository
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class PostService (
    private val memberRepository: MemberRepository,
    private val postRepository : PostRepository,
) {

    fun findAll(sort: String?, nickname: String?) : List<PostResponse> {

        return if(sort == "desc"){

            postRepository.findAllByOrderByCreatedAtDesc()

        } else {

            postRepository.findAllByOrderByCreatedAtAsc()

        } .map { PostResponse.from(it)}

    }

    fun findById(postId: Long): RetrievePostResponse {

       val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

       return RetrievePostResponse.from(foundPost)

    }

    @Transactional
    fun create(request: CreatePostRequest, userPrincipal: UserPrincipal) : PostResponse {

        val (title, description) = request

        val member = memberRepository.findByIdOrNull(userPrincipal.id) ?: throw ModelNotFoundException("User", userPrincipal.id)

        val post = Post.of(
            request.title, request.description, member)

        val createdPost = postRepository.save(post)

        return PostResponse.from(createdPost)

    }

    @Transactional
    fun update(postId: Long, request: UpdatePostRequest, member: Member): PostResponse {

        val (title, description) = request

        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        foundPost.checkAuthorization(member)

        foundPost.updatePostField(title, description)

        // 수정 요청 아이디와 foundPost 아이디가 같은지 확인

        return PostResponse.from(foundPost)
    }

    @Transactional
    fun delete(postId: Long, userPrincipal: UserPrincipal) {

        val foundPost = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        foundPost.checkAuthorization(userPrincipal.to())

        postRepository.deleteById(postId)

    }
}
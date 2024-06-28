package com.teamsparta.assignment.domain.post.service

import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.member.repository.MemberRepository
import com.teamsparta.assignment.domain.post.model.Post
import com.teamsparta.assignment.domain.post.repository.PostRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.repository.findByIdOrNull


class PostServiceTest : BehaviorSpec({
    // Repository mocking !
    val postRepository = mockk<PostRepository>()
    val memberRepository = mockk<MemberRepository>()

    // 저장된 게시물, 맴버 생성
    val savedMember = Member(1L, "my nickname", "my password")
    val savedPost = Post(
        title = "title",
        description = "description",
        member = savedMember
    ).apply { id = 1L }


    // 존재하는 게시물, 존재하지 않는 게시물에 대해 동작하는지 ?
    every { postRepository.findByIdOrNull(1L) } returns savedPost
    every { postRepository.findByIdOrNull(100L) } returns null

    // 존재하는 맴버와 존재하지 않는 맴버에 대한 동작?
    every { memberRepository.findByIdOrNull(1L) } returns savedMember
    every { memberRepository.findByIdOrNull(100L) } returns null


    Given("저장된 게시물 ID가 1일 경우") {
        val targetPostId = 1L

        When("postRepository findByIdOrNull 호출할 시") {
            val result = postRepository.findByIdOrNull(targetPostId)

            Then("null이 아니어야 한다.") {
                result shouldBe savedPost

                }
            }
        }

    Given("저장된 게시물 ID가 100일 경우") {
        val targetPostId = 100L

        When("postRepository findByIdOrNull 호출할 시") {
            val result = postRepository.findByIdOrNull(targetPostId)

            Then("null이 나와야 한다.")
            result shouldNotBe savedPost
        }
    }

    Given("저장된 멤버 ID가 1일 경우") {
        val targetMemberId = 1L

        When("memberRepository findByIdOrNull 호출할 시") {
            val result = memberRepository.findByIdOrNull(targetMemberId)

            Then("null이 아니어야 한다.")
            result shouldBe savedMember
        }
    }

    Given("저장되지 않는 멤버 ID가 100일 경우") {
        val targetMemberId = 100L

        When("memberRepository findByIdOrNull 호출할 시") {
            val result = memberRepository.findByIdOrNull(targetMemberId)

            Then("null이 나와야 한다.")
            result shouldNotBe savedMember
        }
    }
})
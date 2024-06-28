package com.teamsparta.assignment.domain.post.service

import com.teamsparta.assignment.domain.exception.ModelNotFoundException
import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.member.repository.MemberRepository
import com.teamsparta.assignment.domain.post.dto.CreatePostRequest
import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import com.teamsparta.assignment.domain.post.model.Post
import com.teamsparta.assignment.domain.post.repository.PostRepository
import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import net.bytebuddy.matcher.ElementMatchers.returns
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

    every { postRepository.save(any()) } returns savedPost

    val postService = PostService(memberRepository, postRepository)

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

    Given("게시물을 생성할 경우") {
        val request = CreatePostRequest("title", "description")
        val userPrincipal = UserPrincipal(1L, "my nickname")
        When("create 메서드가 호출할 시") {
            val result = postService.create(request, userPrincipal)
            Then("PostResponse를 반환") {
                result.title shouldBe request.title
                result.description shouldBe request.description
            }
        }

        When("만약 유효하지 않은 사용자가 게시물을 생성할 경우") {
            every { memberRepository.findByIdOrNull(userPrincipal.id) } returns null
            Then("ModelNotFoundException을 던진다.")
                shouldThrow<ModelNotFoundException> {
                    postService.create(request, userPrincipal)
                }
         }
    }

    Given("게시물을 수정할 경우") {
        val request = UpdatePostRequest("title", "description")
        val member = savedMember

        When("update 메서드가 호출할 시") {
            every { postRepository.findByIdOrNull(1L) } returns savedPost
            val result = postService.update(1L, request, member)
            Then("PostResponse를 반환")
            result.title shouldBe request.title
            result.description shouldBe request.description
        }

        When("만약 존재하지 않는 게시물을 수정하려고 할 경우") {
            every { postRepository.findByIdOrNull(100L) } returns null
            Then("ModelNotFoundException을 던진다.") {
                shouldThrow<ModelNotFoundException> {
                    postService.update(100L, request, member)
                }
            }
        }
    }

    Given ("게시물을 삭제할 경우") {
        val userPrincipal = UserPrincipal(1L, "my nickname")

        When("delete 메서드가 호출할 시") {
            every { postRepository.findByIdOrNull(1L) } returns savedPost
            every { postRepository.deleteById(1L) } returns Unit
            postService.delete(1L, userPrincipal)
            Then("예외를 던지지 않아도 괜찮음")
        }

        When("만약 존재하지 않는 게시물을 삭제하려고 할 경우") {
            every { postRepository.findByIdOrNull(100L) } returns null
            Then("ModelNotFoundException을 던진다.") {
                shouldThrow<ModelNotFoundException> {
                    postService.delete(100L, userPrincipal)
                }
            }
        }
    }

})
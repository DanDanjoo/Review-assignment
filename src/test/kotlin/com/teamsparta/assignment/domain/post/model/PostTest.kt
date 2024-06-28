package com.teamsparta.assignment.domain.post.model

import com.teamsparta.assignment.domain.exception.InvalidContentException
import com.teamsparta.assignment.domain.exception.InvalidTitleException
import com.teamsparta.assignment.domain.member.model.Member
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe


class PostTest : BehaviorSpec({

    Given("게시물 정보가 주어졌을 경우") {
        val title = "title"
        val description = "description"
        val member = Member(1L, "my nickname", "my password")

        When("게시물 생성자를 호출할 시") {
        val result = Post(
            title = title,
            description = description,
            member = member
        )

            Then("게시물의 제목, 설명, 작성자, 생성일시 및 수정일시가 정확히 설정되어야 한다") {
                result.title shouldBe title
                result.description shouldBe description
                result.member shouldBe member
                result.createdAt shouldBe result.updatedAt
        }
    }
}

    Given("게시글 제목이 비어있을 경우") {
        val title = ""
        val description = "description"
        val member = Member(1L, "my nickname", "my password")
        When("게시물 생성자를 호출할 시") {
            Then("InvalidTitleException이 발생해야 한다.") {
                shouldThrow<InvalidTitleException> {
                    Post.of(title,description,member)
                }
            }
        }
    }

    Given("게시글 제목이 500자를 초과할 경우") {
        val title = "a".repeat(501)
        val description = "description"
        val member = Member(1L, "my nickname", "my password")
        When("게시물 생성자를 호출할 시") {
            Then("InvalidTitleException이 발생해야 한다.") {
                shouldThrow<InvalidTitleException> {
                    Post.of(title,description,member)
                }
            }
        }
    }

    Given("게시글 내용이 비어있을 경우") {
        val title = "title"
        val description = ""
        val member = Member(1L, "my nickname", "my password")
        When("게시물 생성자를 호출할 시") {
            Then("InvalidContentException이 발생해야 한다.") {
                shouldThrow<InvalidContentException> {
                    Post.of(title,description,member)
                }
            }
        }
    }

    Given("게시글의 내용이 5000자를 초과할 경우") {
        val title = "title"
        val description = "a".repeat(5001)
        val member = Member(1L, "my nickname", "my password")
        When("게시물의 생설자를 호출할 시") {
            Then("InvalidContentException이 발생해야 한다.") {
                shouldThrow<InvalidContentException> {
                    Post.of(title,description,member)
                }
            }
        }
    }
})
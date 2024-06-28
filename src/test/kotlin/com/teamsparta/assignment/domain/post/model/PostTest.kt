package com.teamsparta.assignment.domain.post.model

import com.teamsparta.assignment.domain.member.model.Member
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
})
package com.teamsparta.assignment.domain.member.controller

import com.teamsparta.assignment.domain.member.dto.MemberLoginRequest
import com.teamsparta.assignment.domain.member.dto.MemberResponse
import com.teamsparta.assignment.domain.member.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.member.service.MemberService
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus



class MemberControllerTest : BehaviorSpec({
    val memberService = mockk<MemberService>()
    val memberController = MemberController(memberService)

    Given("회원가입 요청이 들어왔을 경우") {
        val request = MemberSignupRequest(nickname = "my nickname", password = "my password", passwordConfirmation = "my password")
        val savedMember = Member(id = 1L, nickname = "my nickname", password = "my password")
        val response = MemberResponse.from(savedMember)

        every { memberService.signUp(request) } returns response

        When("signUp 메서드를 호출할 시") {
            val result = memberController.signUp(request)
            Then("상태 코드는 CREATED") {
                result.statusCode shouldBe HttpStatus.CREATED
            }
        }
    }

    Given("로그인 요청이 들어왔을 경우") {
        val request = MemberLoginRequest(nickname = "my nickname", password = "my password")
        val response = MemberResponse(id = 1L, nickname = "my nickname")

        every { memberService.logIn(request) } returns response

        When("logIn 메서드를 호출할 시") {
            val result = memberController.logIn(request)
            Then("상태 코드는 OK, MemberResponse가 반환되어야 한다.") {
                result.statusCode shouldBe HttpStatus.OK
                result.body shouldBe response
            }
        }
    }
})
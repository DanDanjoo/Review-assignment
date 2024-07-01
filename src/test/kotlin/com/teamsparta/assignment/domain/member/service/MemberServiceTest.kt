package com.teamsparta.assignment.domain.member.service

import com.teamsparta.assignment.domain.exception.LoginValidationException
import com.teamsparta.assignment.domain.exception.NicknameDuplicateException
import com.teamsparta.assignment.domain.exception.NicknameInvalidException
import com.teamsparta.assignment.domain.exception.PasswordMismatchException
import com.teamsparta.assignment.domain.member.dto.MemberLoginRequest
import com.teamsparta.assignment.domain.member.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.member.repository.MemberRepository
import com.teamsparta.assignment.infra.security.jwt.JwtPlugin
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder

class MemberServiceTest : BehaviorSpec({

    val memberRepository = mockk<MemberRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val jwtPlugin = mockk<JwtPlugin>()
    val memberService = MemberService(memberRepository, passwordEncoder, jwtPlugin)

    Given("회원가입 요청이 들어왔을 경우") {
        val request = MemberSignupRequest(
            nickname = "validNick1",
            password = "validPassword",
            passwordConfirmation = "validPassword"
        )

        When("유효한 요청이 주어졌을 시") {
            every { memberRepository.existsByNickname(request.nickname) } returns false
            every { passwordEncoder.encode(request.password) } returns "encodedPassword"
            every { memberRepository.save(any()) } returns Member(
                id = 1L,
                nickname = request.nickname,
                password = "encodedPassword"
            )

            val result = memberService.signUp(request)

            Then("회원가입 성공") {
                result.nickname shouldBe request.nickname
                verify { memberRepository.save(any()) }
            }
        }

        When("닉네임이 이미 존재할 시") {
            every { memberRepository.existsByNickname(request.nickname) } returns true
            Then("NicknameDuplicateException이 발생해야 한다.") {
                shouldThrow<NicknameDuplicateException> {
                    memberService.signUp(request)
                }
            }
        }

        When("비밀번호와 비밀번호 확인이 일치하지 않을 시") {
            val invalidRequest = request.copy(passwordConfirmation = "invalidPassword")
            Then("PasswordMismatchException이 발생해야 한다.") {
                shouldThrow<PasswordMismatchException> {
                    memberService.signUp(invalidRequest)
                }
            }
        }

        When("닉네임이 최소 3자 이상 10자 이하가 아닐 시") {
            val invalidNicknameRequests = listOf(
                MemberSignupRequest(
                    nickname = "na",
                    password = "validPassword",
                    passwordConfirmation = "validPassword"
                ),
                MemberSignupRequest(
                    nickname = "tooLongNickname",
                    password = "validPassword",
                    passwordConfirmation = "validPassword"
                )
            )

            invalidNicknameRequests.forEach { request ->
                Then("NicknameInvalidException이 발생해야 한다. 닉네임: ${request.nickname}") {
                    shouldThrow<NicknameInvalidException> {
                        memberService.signUp(request)
                    }
                }


                When("닉네임에 알파벳 대소문자 또는 숫자가 포함되지 않을 경우") {
                    val invalidCharNicknameRequest = MemberSignupRequest(
                        nickname = "!!!",
                        password = "validPassword",
                        passwordConfirmation = "validPassword"
                    )

                    Then("NicknameInvalidException이 발생해야 한다.") {
                        shouldThrow<NicknameInvalidException> {
                            memberService.signUp(invalidCharNicknameRequest)
                        }
                    }
                }
            }
        }
    }

        Given("로그인 요청이 들어왔을 경우") {

            val request = MemberLoginRequest(
                nickname = "validNickname",
                password = "validPassword"
            )

            val member = Member(
                id = 1L,
                nickname = request.nickname,
                password = "encodedPassword"
            )

            When("유효한 요청이 주어졌을 시") {
                every { memberRepository.findByNickname(request.nickname) } returns member
                every { passwordEncoder.matches(request.password, member.password) } returns true
                every { jwtPlugin.generateAccessToken(any(), any()) } returns "jwtToken"
                val result = memberService.logIn(request)
                Then("로그인 성공") {
                    result.nickname shouldBe request.nickname
                    result.token shouldBe "jwtToken"
                }
            }

            When("닉네임이 존재하지 않을 시") {
                every { memberRepository.findByNickname(request.nickname) } returns null
                Then("LoginValidationException이 발생해야 한다.") {
                    shouldThrow<LoginValidationException> {
                        memberService.logIn(request)
                    }
                }
            }

            When("비밀번호가 일치하지 않을 시") {
                every { memberRepository.findByNickname(request.nickname) } returns member
                every { passwordEncoder.matches(request.password, member.password) } returns false
                Then("LoginValidationException이 발생해야 한다.") {
                    shouldThrow<LoginValidationException> {
                        memberService.logIn(request)
                    }
                }
            }
        }


})
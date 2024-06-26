package com.teamsparta.assignment.domain.member.service

import com.teamsparta.assignment.domain.exception.*
import com.teamsparta.assignment.domain.member.dto.MemberLoginRequest
import com.teamsparta.assignment.domain.member.dto.MemberResponse
import com.teamsparta.assignment.domain.member.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.member.repository.MemberRepository
import com.teamsparta.assignment.infra.security.jwt.JwtPlugin
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern


@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {

    @Transactional
    fun signUp(request: MemberSignupRequest): MemberResponse {
        val (nickname, password) = request
        validateNickname(nickname)
        validatePassword(password, nickname)


        if (request.password != request.passwordConfirmation) {
            throw PasswordMismatchException()
        }

        if (memberRepository.existsByNickname(request.nickname)) {
            throw NicknameDuplicateException()
        }
        val member = Member(
            nickname = request.nickname,
            password = passwordEncoder.encode(request.password)
        )

        val saveMember = memberRepository.save(member)

        return MemberResponse.from(saveMember)
    }

    @Transactional
    fun logIn(request: MemberLoginRequest): MemberResponse {
        val member = memberRepository.findByNickname(request.nickname)
            ?: throw LoginValidationException()

        if (member.nickname != request.nickname ||
            !passwordEncoder.matches(request.password, member.password)){
            throw LoginValidationException()
        }

        val token = jwtPlugin.generateAccessToken(
            subject = member.id.toString(),
            nickname = member.nickname
        )

        return MemberResponse.from(member, token)
    }
}


    private fun validateNickname(nickname: String) {

        if (!Pattern.matches(
                "^[a-zA-Z0-9]{3,10}$",
                nickname
            // `^` 문자열 시작, `[a-zA-Z0-9]` 알파벳 대소문자, 숫자 중 하나를 의미함, `3,` 최소 3자 이상, `$` 문자열 끝맺음
            )
        ) {
            throw NicknameInvalidException()
        }
    }

    private fun validatePassword(password: String, nickname: String) {

        if (!Pattern.matches(
                "^.{4,16}$",
                password
            // `^` 문자열 시작, `.{4,16}` 최소 4자 이상 ~ 16자 이하, `$` 문자열 끝맺음
        )
        ) {
            throw PasswordLengthException()
        }
        if (password.contains(nickname)) {

            throw PasswordContainsNicknameException()
        }

    }

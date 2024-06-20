package com.teamsparta.assignment.domain.user.service

import com.teamsparta.assignment.domain.exception.*
import com.teamsparta.assignment.domain.user.dto.MemberResponse
import com.teamsparta.assignment.domain.user.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.user.model.Member
import com.teamsparta.assignment.domain.user.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern


@Service
class MemberService (
   private val memberRepository: MemberRepository
) {

    @Transactional
    fun signUp(request: MemberSignupRequest): MemberResponse {
        val (nickname, password) = request
        validateNickname(nickname)
        validatePassword(password, nickname)


        if(request.password != request.passwordConfirmation) {
            throw PasswordValidationException.PasswordMismatchException()
        }

        if (memberRepository.existsByNickname(request.nickname)) {
            throw NicknameValidationException.NicknameDuplicateException()
        }
        val member = Member(nickname = request.nickname, password = request.password)

        val saveMember = memberRepository.save(member)

        return MemberResponse.from(saveMember)
    }


    private fun validateNickname(nickname: String) {

        if (!Pattern.matches(
                "^[a-zA-Z0-9]{3,10}$",
                nickname
            // `^` 문자열 시작, `[a-zA-Z0-9]` 알파벳 대소문자, 숫자 중 하나를 의미함, `3,` 최소 3자 이상, `$` 문자열 끝맺음
            )
        ) {
            throw NicknameValidationException.NicknameInvalidException()
        }
    }

    }

    private fun validatePassword(password: String, nickname: String) {

        if (!Pattern.matches(
                "^.{4,16}$",
                password
            // `^` 문자열 시작, `.{4,16}` 최소 4자 이상 ~ 16자 이하, `$` 문자열 끝맺음
        )
        ) {
            throw PasswordValidationException.PasswordLengthException()
        }
        if (password.contains(nickname)) {

            throw PasswordValidationException.PasswordContainsNicknameException()
        }

    }

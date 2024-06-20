package com.teamsparta.assignment.domain.user.service

import com.teamsparta.assignment.domain.user.dto.MemberResponse
import com.teamsparta.assignment.domain.user.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.user.model.Member
import com.teamsparta.assignment.domain.user.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class MemberService (
   private val memberRepository: MemberRepository
) {

    @Transactional
    fun signUp(request: MemberSignupRequest): MemberResponse {
        if (memberRepository.existsByNickname(request.nickname)) {
            throw RuntimeException("중복된 닉네임입니다.")
        }
        val member = Member(nickname = request.nickname, password = request.password)

        val saveMember = memberRepository.save(member)

        return MemberResponse.from(saveMember)
    }
}
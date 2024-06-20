package com.teamsparta.assignment.domain.user.service

import com.teamsparta.assignment.domain.user.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.user.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.reflect.Member

@Service
class MemberService (
    memberRepository: MemberRepository
) {

    @Transactional
    fun signUp(request: MemberSignupRequest) {
        if (memberRepository.existsByNickName(request.nickname)) {
            throw RuntimeException("중복된 닉네임입니다.")
        }
        val member = Member(nickname = request.nickname, password = request.password)
        memberRepository.save(member)
    }
}
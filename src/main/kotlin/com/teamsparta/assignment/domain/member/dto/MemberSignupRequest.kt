package com.teamsparta.assignment.domain.member.dto

data class MemberSignupRequest (
    val nickname: String,
    val password: String,
    val passwordConfirmation: String // 비밀번호 확인
)

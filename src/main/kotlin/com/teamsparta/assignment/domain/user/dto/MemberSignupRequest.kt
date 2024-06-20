package com.teamsparta.assignment.domain.user.dto

data class MemberSignupRequest (
    val nickname: String,
    val password: String,
    val passwordConfirmation: String // 비밀번호 확인
)

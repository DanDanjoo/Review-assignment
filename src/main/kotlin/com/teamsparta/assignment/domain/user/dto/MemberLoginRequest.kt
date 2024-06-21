package com.teamsparta.assignment.domain.user.dto

data class MemberLoginRequest (
    val nickname: String,
    val password: String,
)
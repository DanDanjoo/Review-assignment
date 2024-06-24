package com.teamsparta.assignment.domain.member.dto

data class MemberLoginRequest (
    val nickname: String,
    val password: String,
)
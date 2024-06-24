package com.teamsparta.assignment.domain.post.dto

data class UpdatePostRequest(
    val nickname: String,
    val title: String,
    val description: String
)
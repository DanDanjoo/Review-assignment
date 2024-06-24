package com.teamsparta.assignment.domain.post.dto

data class CreatePostRequest (
    val nickname : String,
    val title : String,
    val description : String,
)
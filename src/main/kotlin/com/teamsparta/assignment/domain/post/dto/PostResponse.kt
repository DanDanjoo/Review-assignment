package com.teamsparta.assignment.domain.post.dto

import com.teamsparta.assignment.domain.post.model.Post

data class PostResponse (
    val id : Long?,
    val title : String,
    val description : String,
) {

    companion object {

        fun from(posts : Post) : PostResponse {
            return PostResponse(
                posts.id,
                posts.title,
                posts.description,
            )
        }
    }
}
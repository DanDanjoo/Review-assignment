package com.teamsparta.assignment.domain.post.dto

import com.teamsparta.assignment.domain.post.comment.dto.CommentResponse
import com.teamsparta.assignment.domain.post.model.Post

data class RetrievePostResponse (
    val title : String,
    val description : String,
    val comment : List<CommentResponse>
) {

    companion object {

        fun from(posts : Post) : RetrievePostResponse {
            return RetrievePostResponse (
                posts.title,
                posts.description,
                posts.comments.map { CommentResponse.from(it) }
            )
        }
    }
}
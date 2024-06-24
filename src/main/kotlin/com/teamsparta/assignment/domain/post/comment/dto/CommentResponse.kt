package com.teamsparta.assignment.domain.post.comment.dto

import com.teamsparta.assignment.domain.post.comment.model.Comment

data class CommentResponse(
    val content: String,
) {

    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                comment.content
            )
        }
    }
}
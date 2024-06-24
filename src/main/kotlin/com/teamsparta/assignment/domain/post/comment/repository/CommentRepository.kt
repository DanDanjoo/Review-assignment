package com.teamsparta.assignment.domain.post.comment.repository

import com.teamsparta.assignment.domain.post.comment.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long>
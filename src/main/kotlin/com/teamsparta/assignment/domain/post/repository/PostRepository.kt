package com.teamsparta.assignment.domain.post.repository

import com.teamsparta.assignment.domain.post.model.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long> {
}
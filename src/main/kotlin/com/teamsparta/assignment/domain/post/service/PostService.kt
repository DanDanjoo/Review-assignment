package com.teamsparta.assignment.domain.post.service

import com.teamsparta.assignment.domain.post.repository.PostRepository
import org.springframework.stereotype.Service


@Service
class PostService (
    private val postRepository : PostRepository
) {

}
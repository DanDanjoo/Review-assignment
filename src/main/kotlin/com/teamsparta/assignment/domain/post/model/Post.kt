package com.teamsparta.assignment.domain.post.model

import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "post")
class Post (

    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    fun updatePostField(request: UpdatePostRequest) {
        title = request.title
        description = request.description
        nickname = request.nickname
    }
}
package com.teamsparta.assignment.domain.post.comment.model

import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.post.model.Post
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Comment (

    @Column
    var content: String,

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    ){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
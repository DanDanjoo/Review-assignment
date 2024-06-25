package com.teamsparta.assignment.domain.post.model

import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.post.dto.UpdatePostRequest
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "post")
class Post (

    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member : Member,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

) {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    fun checkAuthorization(requestMember : Member) {
        if(requestMember.id != member.id) {
            throw Exception("not permitted")

        }
    }

    fun updatePostField(request: UpdatePostRequest) {
        title = request.title
        description = request.description

    }
}
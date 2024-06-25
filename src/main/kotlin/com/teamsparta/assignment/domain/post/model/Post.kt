package com.teamsparta.assignment.domain.post.model

import com.teamsparta.assignment.domain.exception.PostInvariantException
import com.teamsparta.assignment.domain.member.model.Member
import com.teamsparta.assignment.domain.post.comment.model.Comment
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

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    val comments : List<Comment> = emptyList()


    fun checkAuthorization(requestMember : Member) {
        if(requestMember.id != member.id) {
            throw Exception("not permitted")

        }
    }

    fun updatePostField(title: String, description: String) {
        validateTitleLength(title)
        validateDescription(description)

        this.title = title
        this.description = description
        this.updatedAt = LocalDateTime.now()

    }

    companion object {

        private fun validateTitleLength(title: String) {
            if (title.isEmpty() || title.length > 500) {
                throw PostInvariantException.InvalidTitleException()
            }
        }

        private fun validateDescription(description: String) {
            if (description.isEmpty() || description.length > 5000) {
                throw PostInvariantException.InvalidContentException()
            }
        }


        fun of(title: String, description: String, member: Member) : Post {
            validateTitleLength(title)
            validateDescription(description)

            val timestamp = LocalDateTime.now()

            return Post(
                title = title,
                description = description,
                member = member,
                createdAt = timestamp,
                updatedAt = timestamp
            )
        }

        }
    }


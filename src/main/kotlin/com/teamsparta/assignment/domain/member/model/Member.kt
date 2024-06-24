package com.teamsparta.assignment.domain.member.model

import jakarta.persistence.*

@Entity
class Member (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "password", nullable = false)
    val password: String,

)

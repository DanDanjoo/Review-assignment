package com.teamsparta.assignment.domain.user.model

import jakarta.persistence.*



@Entity
class Member (

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "password", nullable = false)
    val password: String,

) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}


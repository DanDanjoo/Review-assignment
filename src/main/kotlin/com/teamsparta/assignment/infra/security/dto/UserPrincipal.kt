package com.teamsparta.assignment.infra.security.dto

import com.teamsparta.assignment.domain.member.model.Member

data class UserPrincipal (
    val id : Long,
    val nickname : String
) {
    fun to() : Member {

        return Member (
            id = id,
            nickname = nickname,
            password = ""
        )
    }
}
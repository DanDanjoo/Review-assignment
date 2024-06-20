package com.teamsparta.assignment.domain.user.dto

import com.teamsparta.assignment.domain.user.model.Member

data class MemberResponse (
    val id : Long?,
    val nickname : String
) {
    companion object {
        fun from(saveMember: Member): MemberResponse {

            return MemberResponse(
                saveMember.id,
                saveMember.nickname
            )

        }
    }
}

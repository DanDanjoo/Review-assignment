package com.teamsparta.assignment.domain.member.dto

import com.teamsparta.assignment.domain.member.model.Member

data class MemberResponse (
    val id : Long?,
    val nickname : String,
) {
    var token : String? = null

    companion object {
        fun from(saveMember: Member): MemberResponse {

            return MemberResponse(
                saveMember.id,
                saveMember.nickname

            )

        }

        fun from(saveMember: Member, token : String): MemberResponse {

            val memberResponse = MemberResponse(
                saveMember.id,
                saveMember.nickname
            )
            memberResponse.token = token

            return memberResponse
        }
    }
}

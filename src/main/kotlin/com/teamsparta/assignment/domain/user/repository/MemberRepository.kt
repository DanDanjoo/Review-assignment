package com.teamsparta.assignment.domain.user.repository

import com.teamsparta.assignment.domain.user.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>  {

    fun existsByNickname(nickname: String): Boolean

    fun existsByPassword(password: String): Boolean
}
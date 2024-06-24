package com.teamsparta.assignment.domain.member.controller

import com.teamsparta.assignment.domain.member.dto.MemberLoginRequest
import com.teamsparta.assignment.domain.member.dto.MemberResponse
import com.teamsparta.assignment.domain.member.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/members")
class MemberController (
    private val memberService: MemberService

) {
    @PostMapping("/signup")
    fun signUp(@RequestBody request: MemberSignupRequest): ResponseEntity<MemberResponse> {
        memberService.signUp(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/login")
    fun logIn(@RequestBody request: MemberLoginRequest): ResponseEntity<MemberResponse> {
        val response = memberService.logIn(request)
        return ResponseEntity.ok(response)
    }

}
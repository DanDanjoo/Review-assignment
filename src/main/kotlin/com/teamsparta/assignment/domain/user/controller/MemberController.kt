package com.teamsparta.assignment.domain.user.controller

import com.teamsparta.assignment.domain.user.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.user.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/users")
class MemberController (
    private val memberService: MemberService

) {
    @PostMapping("/signup")
    fun signUp(@RequestBody request: MemberSignupRequest): ResponseEntity<Unit> {
        memberService.signUp(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
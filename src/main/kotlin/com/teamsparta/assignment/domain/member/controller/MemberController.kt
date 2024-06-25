package com.teamsparta.assignment.domain.member.controller

import com.teamsparta.assignment.domain.member.dto.MemberLoginRequest
import com.teamsparta.assignment.domain.member.dto.MemberResponse
import com.teamsparta.assignment.domain.member.dto.MemberSignupRequest
import com.teamsparta.assignment.domain.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/members")
class MemberController (
    private val memberService: MemberService

) {
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "계정을 생성합니다.")
    fun signUp(@RequestBody request: MemberSignupRequest): ResponseEntity<MemberResponse> {
        memberService.signUp(request)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "생성된 계정으로 로그인합니다.")
    fun logIn(@RequestBody request: MemberLoginRequest): ResponseEntity<MemberResponse> {
        val response = memberService.logIn(request)
        return ResponseEntity.ok(response)
    }

}
package com.teamsparta.assignment.infra.security.jwt

import com.teamsparta.assignment.infra.security.dto.UserPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtAuthenticationFilter (
    private val jwtPlugin: JwtPlugin
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
       val jwt = request.getBearerToken()

        jwt?.let {token ->
            jwtPlugin.validateToken(token)
                .onSuccess { decoded ->
                  val memberId = decoded.payload.subject.toLong()
                  val nickname =  decoded.payload.get("nickname", String::class.java)
                    // 토큰을 받아서 해독했음 ( 인증 완료 )

                  val userPrincipal = UserPrincipal(memberId, nickname)
                  val detail = WebAuthenticationDetailsSource().buildDetails(request)

                  val auth = JwtAuthenticationToken(userPrincipal, detail)
                    // 로그인한 사람의 정보를 담아주는 객체, request에 남아있는 AuthenticationDetail 정보들 detail에 담고

                  SecurityContextHolder.getContext().authentication = auth
                    // 토큰 모아준다음 인증 객체 만든 후 ContextHolder에 끼워 맞춰주는 작업
                }
        }
    }
    // 헤더에서 요청받은 값을 꺼낸 후 Bearer 글자 제거 후 순수 토큰 값만 받아올거임
    private fun HttpServletRequest.getBearerToken() : String? {
       val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
       return Regex("^Bearer (.+?)$").find(headerValue)?.groupValues?.get(1)
    // `^Bearer` 문자열이 Bearer 로 시작, `(.+?)` 괄호 안 부분은 토큰의 내용이며 `.+?`은 최소한의 문자열을 의미함, `$` 문자열 끝맺음

    }
}

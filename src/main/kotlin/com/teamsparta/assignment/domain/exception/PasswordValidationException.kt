package com.teamsparta.assignment.domain.exception

open class PasswordValidationException(message: String) : RuntimeException(message) {

    class PasswordMismatchException : PasswordValidationException("비밀번호가 서로 일치하지 않습니다.")
    class PasswordLengthException : PasswordValidationException ("비밀번호는 최소 4자 이상이어야 합니다.")
    class PasswordContainsNicknameException : PasswordValidationException ("비밀번호에 닉네임을 포함할 수 없습니다")

}
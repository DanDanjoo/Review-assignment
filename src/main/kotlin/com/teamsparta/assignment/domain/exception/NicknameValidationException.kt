package com.teamsparta.assignment.domain.exception

open class NicknameValidationException(message: String) : RuntimeException(message) {

    class NicknameDuplicateException : NicknameValidationException("중복된 닉네임입니다.")
    class NicknameInvalidException : NicknameValidationException("닉네임은 최소 3자 이상 10자 이하, 알파벳 대소문자, 숫자를 포함합니다.")

}
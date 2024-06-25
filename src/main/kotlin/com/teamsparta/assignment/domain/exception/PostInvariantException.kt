package com.teamsparta.assignment.domain.exception

open class PostInvariantException(message: String) : RuntimeException(message) {

    class InvalidTitleException: PostInvariantException("제목은 비어있지 않고 500자 이하여야 합니다.")
    class InvalidContentException : PostInvariantException("내용은 비어있지 않고 5000자 이하여야 합니다.")

}
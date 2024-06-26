package com.teamsparta.assignment.domain.exception

class InvalidTitleException : RuntimeException("제목은 비어있지 않고 500자 이하여야 합니다.")
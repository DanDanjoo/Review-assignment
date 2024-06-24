package com.teamsparta.assignment.domain.exception

data class ModelNotFoundException(val modelName: String, val id: Long):
    RuntimeException("Model $modelName not found with id $id")
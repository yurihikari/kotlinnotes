package com.example.mynotes

import java.util.UUID

data class Notes(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String
)


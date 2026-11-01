package com.example.flashquiz

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "flashcard_table")
data class Flashcard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    val answer: String
)
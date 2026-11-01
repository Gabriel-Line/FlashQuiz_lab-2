package com.example.flashquiz

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcard_table")
    fun getAllCards(): List<Flashcard>

    @Insert
    fun insertCard(flashcard: Flashcard)
}
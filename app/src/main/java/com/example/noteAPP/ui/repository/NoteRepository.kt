package com.example.todoapp.ui.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.noteAPP.ui.model.Note

//Class that contains subclasses
sealed class ValidationResult {
    object Success: ValidationResult()
    data class Failure(val errors: List<String>) : ValidationResult()
}

class NoteRepository : ViewModel() {
    var noteList by mutableStateOf(
        listOf<Note>()
    )
        private set //Expose as read-only

    private fun validateInput(title: String, text: String): ValidationResult {
        val errors: MutableList<String> = mutableListOf()

        if(title.length < 3) errors.add("Title must be at least 3 characters")
        if(title.length > 50) errors.add("Title must not exceed 50 characters")
        if(text.length > 120) errors.add("Text must not exceed 120 characters")

       return if(errors.isEmpty()) {
           ValidationResult.Success
       } else {
           ValidationResult.Failure(errors = errors)
       }
    }

    fun noteCount(): Int = noteList.size

    fun addNote(title: String, text: String): ValidationResult {
        val result: ValidationResult = validateInput(title, text)

        if(result is ValidationResult.Failure) {
            return result
        }

        noteList += Note(
            id = (noteList.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            text = text,
        )

        return ValidationResult.Success
    }

    fun modifyNote(noteId: Int, title: String, text: String): ValidationResult {
        val result: ValidationResult = validateInput(title, text)

        if(result is ValidationResult.Failure) {
            return result
        }

        noteList.find { it.id == noteId }?.apply {
            this.title = title
            this.text = text
        }

        return ValidationResult.Success
    }

    fun removeNote(note: Note) {
        noteList = noteList.filter { it.id != note.id }
    }
}
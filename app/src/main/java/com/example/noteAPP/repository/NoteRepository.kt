package com.example.todoapp.ui.repository

import androidx.lifecycle.ViewModel
import com.example.todoapp.ui.model.Note

//Class that contains subclasses
sealed class ValidationResult {
    // Class that hold dara
    data class Failure(val errors: List<String>) : ValidationResult()
}

class NoteRepository : ViewModel() {
    var noteList: MutableList<Note> = mutableListOf(
        Note(1, "Hi", "World"),
        Note(2, "Hi", "World"),
    )
        private set //Setter only available in this file

    private fun validateInput(title: String, text: String): ValidationResult {
        val errors: MutableList<String> = mutableListOf()

        if(title.length < 3) errors.add("Title must be at least 3 characters")
        if(title.length > 50) errors.add("Title must not exceed 50 characters")
        if(text.length > 120) errors.add("Title must not exceed 120 characters")

        return ValidationResult.Failure(errors)
    }

    fun addNote(note: Note): Pair<Boolean, Any> {
        val result: ValidationResult = validateInput(note.title, note.text)

        if(result is ValidationResult.Failure) {
            return Pair(false, result)
        }

        noteList.add(Note(
            id = (noteList.maxOfOrNull { it.id } ?: 0) + 1,
            title = note.title,
            text = note.text,
        ))

        return Pair(true, emptyList<String>())
    }

    fun modifyNote(note: Note): Pair<Boolean, Any> {
        val result = validateInput(note.title, note.text)

        if(result is ValidationResult.Failure) {
            return Pair(false, result)
        }

        noteList.find { it.id == note.id}?.apply {
            title = note.title
            text = note.text
        }

        return Pair(true, emptyList<String>())
    }

    fun removeNote(note: Note) {
        noteList.removeAll { it.id == note.id}
    }
}
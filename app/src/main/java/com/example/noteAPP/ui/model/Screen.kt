package com.example.noteAPP.ui.model

sealed class Screen(val route: String) {
    //Object define and create an instance of a class in a single step
    object Overview : Screen("overview")
    object NoteEdit : Screen("note-edit") {
        fun getNote(noteId: Int) = "$route/$noteId"
    }
    object NoteDetail : Screen("note-detail") {
        fun getNote(noteId: Int) = "$route/$noteId"
    }
}
package com.example.noteAPP.ui.model

sealed class Screen(val route: String) {
    //Object define and create an instance of a class in a single step
    object Overview : Screen("overview")
    object NoteDetail : Screen("note-detail")
    object NoteEdit : Screen("note-edit")
}
package com.example.todoapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.model.Screen
import com.example.todoapp.ui.repository.NoteRepository

@Composable
fun NoteApp(noteRepository: NoteRepository) {
    val navController = rememberNavController()

    NavHost(navController, Screen.Overview.route) {
        composable(Screen.Overview.route) { OverviewScreen(noteRepository, navController)}
        composable(Screen.NoteEdit.route) {}
        composable("${Screen.NoteEdit.route}/{noteId}") {}
        composable("${Screen.NoteDetail.route}/{noteId}") {}
    }
}


//@Composable
//@Preview(showBackground = true)
//fun PreviewDefault() {
//    NoteApp(NoteRepository())
//}
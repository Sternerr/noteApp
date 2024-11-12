package com.example.todoapp.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.noteAPP.ui.components.NoteDetail
import com.example.noteAPP.ui.components.NoteEdit
import com.example.noteAPP.ui.model.Screen
import com.example.todoapp.ui.repository.NoteRepository

@Composable
fun NoteApp() {
    val navController = rememberNavController()
    val noteRepository = NoteRepository()

    NavHost(navController, Screen.Overview.route) {
        composable(Screen.Overview.route) {
            OverviewScreen(
                noteRepository= noteRepository,
                navController = navController
            )
        }
        composable(Screen.NoteEdit.route) {
            NoteEdit(
                noteId = null,
                noteRepository = noteRepository,
                navController = navController,
            )
        }
        composable(
            route ="${Screen.NoteEdit.route}/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })) {

            NoteEdit(
                noteId = it.arguments?.getInt("noteId"),
                noteRepository = noteRepository,
                navController = navController,
            )
        }
        composable(
            route ="${Screen.NoteDetail.route}/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })) {

            NoteDetail(
                noteId = it.arguments?.getInt("noteId"),
                noteRepository = noteRepository,
                navController = navController,
            )
        }
    }
}


//@Composable
//@Preview(showBackground = true)
//fun PreviewDefault() {
//    NoteApp()
//}
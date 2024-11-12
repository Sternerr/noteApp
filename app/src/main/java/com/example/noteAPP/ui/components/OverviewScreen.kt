package com.example.todoapp.ui.components

import android.annotation.SuppressLint
import android.app.LauncherActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todoapp.ui.repository.NoteRepository

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OverviewScreen(noteRepository: NoteRepository, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Notes") })
            HorizontalDivider(
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            BottomAppBarNavigation(
                navController = navController,
                noteRepository = noteRepository
            ) }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .padding(paddingValues)
        ) {
            LazyColumn (
                modifier = Modifier
            ) {
                items(noteRepository.noteList) { note ->
                    SwipeToDismissBox(
                        state = rememberSwipeToDismissBoxState(),
                        backgroundContent = {},
                        enableDismissFromStartToEnd = false,
                    ) {
                        ListItem(
                            headlineContent = { Text(note.title) },
                            supportingContent = { Text(note.text) },
                            colors = ListItemDefaults.colors(Color.LightGray),
                            modifier = Modifier
                                .padding(16.dp)
                                .clip(RoundedCornerShape(16.dp)),
                        )
                    }
                }
            }
        }
    }
}
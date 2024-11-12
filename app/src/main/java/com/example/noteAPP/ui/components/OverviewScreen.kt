package com.example.todoapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteAPP.ui.components.SwipeContainer
import com.example.noteAPP.ui.model.Screen
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
            if(noteRepository.noteList.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                ) {
                    items(
                        noteRepository.noteList,
                        key = {
                            it.id
                        }
                    ) { note ->
                        SwipeContainer(
                            note = note,
                            onDelete = {
                                noteRepository.removeNote(note)
                            },
                            onModify = {
                                navController.navigate(
                                    Screen.NoteEdit.getNote(note.id)
                                )
                            }
                        ) {
                            ListItem(
                                headlineContent = { Text(it.title) },
                                supportingContent = {
                                    Text( text = it.text )
                                },
                                colors = ListItemDefaults.colors(Color.LightGray),
                                modifier = Modifier
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable {
                                        navController.navigate(
                                            Screen.NoteDetail.getNote(note.id)
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}


package com.example.todoapp.ui.components

import android.annotation.SuppressLint
import android.app.LauncherActivity
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteAPP.ui.components.SwipeToDeleteContainer
import com.example.noteAPP.ui.model.Note
import com.example.todoapp.ui.repository.NoteRepository
import kotlinx.coroutines.delay

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
                        SwipeToDeleteContainer(
                            note = note,
                            onDelete = {
                                noteRepository.removeNote(note)
                                Log.d("Remove Note", "${noteRepository.noteList}")
                            },
                        ) {
                            ListItem(
                                headlineContent = { Text(it.title) },
                                supportingContent = { Text(it.text) },
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
}


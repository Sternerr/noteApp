package com.example.noteAPP.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteAPP.ui.model.Note
import com.example.todoapp.ui.repository.NoteRepository
import com.example.todoapp.ui.repository.ValidationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEdit(noteId: Int?, noteRepository: NoteRepository, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf(emptyList<String>()) }

    if(noteId != null && noteId >= 0) {
        noteRepository.noteList.find { it.id == noteId }?.let {
            title = it.title
            text = it.text
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("overview") }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                title = { Text("Note") },
                modifier = Modifier,
                actions = {},
            )
            HorizontalDivider(
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(paddingValues)
        ) {
            if(errorMessage.isNotEmpty()) {
                errorMessage.forEach { error ->
                    Text(text = error, color = Color.Red)
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 0.dp, color = Color.Transparent),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,

                        unfocusedContainerColor = Color.LightGray,
                        focusedContainerColor = Color.LightGray
                    )
                )
                Spacer( modifier = Modifier.height(8.dp))
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Text") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,

                        unfocusedContainerColor = Color.LightGray,
                        focusedContainerColor = Color.LightGray
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                Button(
                    colors = ButtonColors(
                        contentColor = Color.Black,
                        containerColor = Color.LightGray,
                        disabledContentColor = Color.Unspecified,
                        disabledContainerColor = Color.Unspecified,
                    ),
                    onClick = {
                        var result: Any? = null
                        if(noteId == null || noteId < 0) {
                            result = noteRepository.addNote(
                                title = title,
                                text = text
                            )
                        } else {
                            result = noteRepository.modifyNote(
                                noteId = noteId,
                                title = title,
                                text = text,
                            )
                        }

                        if(result is ValidationResult.Failure) {
                            errorMessage = result.errors
                        } else {
                            navController.navigate("overview")
                        }
                    }
                ) {
                    Text("Save Note")
                }
            }
        }
    }
}

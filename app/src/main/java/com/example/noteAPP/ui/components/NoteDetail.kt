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
import com.example.noteAPP.ui.model.Screen
import com.example.todoapp.ui.repository.NoteRepository
import com.example.todoapp.ui.repository.ValidationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetail(noteId: Int?, noteRepository: NoteRepository, navController: NavController) {
    var title by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }

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
                title = { Text(title) },
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
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text)
            }
        }
    }
}

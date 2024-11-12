package com.example.noteAPP.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.noteAPP.ui.model.Note
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeContainer(
    note: Note,
    onDelete: () -> Unit,
    onModify: () -> Unit,
    animationDuration: Int = 500,
    content: @Composable (Note) -> Unit
) {

    var isRemoved by remember {
        mutableStateOf(false)
    }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if(value == SwipeToDismissBoxValue.EndToStart) {
                isRemoved = true
                true
            } else if(value == SwipeToDismissBoxValue.StartToEnd) {
                onModify()
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(key1 = isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete()
        }
    }

    AnimatedVisibility(
        visible = !isRemoved,
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = { SwipeBackground( state = state )},
            content = { content(note) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeBackground(
    state: SwipeToDismissBoxState
) {
    var color: Color? = null
    var icon: ImageVector? = null
    var alignment: Alignment? = null
    if(state.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
        color = Color.Red
        icon = Icons.Default.Delete
        alignment = Alignment.CenterEnd
    } else if(state.dismissDirection == SwipeToDismissBoxValue.StartToEnd){
        color = Color.Green
        icon = Icons.Default.Edit
        alignment = Alignment.CenterStart
    } else {
        color = Color.Transparent
    }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color)
                .padding(16.dp),
            contentAlignment = alignment ?: Alignment.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "a"
                )
            }
        }
    }
}

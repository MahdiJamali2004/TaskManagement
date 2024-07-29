@file:OptIn(ExperimentalMaterial3Api::class)

package com.solo.tasky.feature_note.presentation.noteScreen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo.tasky.feature_note.domain.model.Note
import com.solo.tasky.feature_task.domain.models.TaskColor


@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    note: Note,
    onDeleteNote :(Note) -> Unit,
    onNoteClick: (id: Int, taskColor: TaskColor) -> Unit
) {


    Card(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(12.dp), onClick = {
            onNoteClick(note.id!!, note.noteColor)
        }
    ) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxSize()
                .background(
                    note.noteColor.color,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {


            Canvas(modifier = Modifier.matchParentSize()) {
                val width = constraints.maxWidth.toFloat()
                val height = constraints.maxHeight.toFloat()
                val path = Path().apply {
                    val offset1 = Offset(width * 0.9f, 0f)
                    val offset2 = Offset(width * 0.65f, height * 0.8f)
                    val offset3 = Offset(width * 0.6f, height)
                    val offset4 = Offset(width * 0.8f, height)
                    val offset5 = Offset(width, height * 0.2f)

                    moveTo(offset1.x, offset1.y)
                    quadraticBezierTo(width * 0.65f, height * 0.2f, offset2.x, offset2.y)
                    quadraticBezierTo(width * 0.65f, height * 1, offset3.x, offset3.y)
                    lineTo(offset4.x, offset4.y)
                    quadraticBezierTo(width * 0.7f, height * 0.45f, offset5.x, offset5.y)
                    lineTo(width, -height)
                    close()
                }

                drawPath(path, note.noteColor.lightColor)

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = note.title,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 24.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.ExtraBold
                    )

                    IconButton(onClick = {
                        onDeleteNote(note)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete note",
                            tint = MaterialTheme.colorScheme.secondary
                        )

                    }
                }


                Text(
                    text = note.description,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 6,
                    style = TextStyle(letterSpacing = 0.25.sp),
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp
                )

            }

        }

    }
}



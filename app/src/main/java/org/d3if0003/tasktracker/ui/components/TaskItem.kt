package org.d3if0003.tasktracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if0003.tasktracker.R

@Composable
fun TaskItem() {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier) {
                // Image
                Image(
                    painter = painterResource(id = R.drawable.baseline_image_search_24),
                    contentDescription = null,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(70.dp)
                        .padding(8.dp)
                        .padding(start = 10.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column(modifier = Modifier.weight(1f)) {
                    // Title
                    Text(
                        text = "Title",
                        color = Color.Black, // Lavender color
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )

                    // Description
                    Text(
                        text = "Desc",
                        color = Color.Black,
                        fontSize = 14.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                    )
                }

                // Priority
                Text(
                    text = "Priority",
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 20.dp, start = 90.dp)
                        .width(80.dp)
                )
            }

            // Progress Bar
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(top = 40.dp, start = 180.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp),
                    color = Color.Black,
                    strokeWidth = 4.dp // Default stroke width
                )
            }

            // Delete button
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 45.dp ,end = 10.dp, bottom = 10.dp)
            ) {
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete task",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    TaskItem()
}

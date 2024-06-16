package org.d3if0003.tasktracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.d3if0003.tasktracker.R
import org.d3if0003.tasktracker.model.DataClass

@Composable
fun DataItemCard(data: DataClass, onDeleteClick: (DataClass) -> Unit, onClick: (DataClass) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(data) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = data.dataImage ?: R.drawable.baseline_image_search_24),
                contentDescription = "Task Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = data.dataTitle ?: "No Title",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = data.dataDesc ?: "No Description",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .clickable { onDeleteClick(data) }
                        .padding(4.dp)
                )
            }
        }
    }
}

package org.d3if0003.tasktracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import org.d3if0003.tasktracker.R

@Composable
fun Detail(
    onBackClick: () -> Unit,
    title: String,
    imageUrl: String?,
    priority: String,
    description: String
) {
    Box(
        modifier = Modifier
            .background(Color.White)
    ) {
        // Back Button
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Button",
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .clickable(onClick = onBackClick)
                .size(24.dp),
            tint = Color(0xFF8B8BE7) // Lavender color
        )

        // Detail Content
        Column(
            modifier = Modifier
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = title,
                color = Color(0xFF8B8BE7), // Lavender color
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                textAlign = TextAlign.Center
            )

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl ?: R.drawable.baseline_image_search_24),
                contentDescription = "Detail Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(350.dp, 250.dp) // Correcting the size modifier
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Priority: $priority",
                color = Color(0xFF8B8BE7), // Lavender color
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(20.dp)
            )

            Text(
                text = description,
                color = Color(0xFF8B8BE7), // Lavender color
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    Detail(
        onBackClick = {},
        title = "Sample Task Title",
        imageUrl = null,
        priority = "High",
        description = "This is a sample task description to show how the detail screen will look."
    )
}

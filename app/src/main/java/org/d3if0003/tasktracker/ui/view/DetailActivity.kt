package org.d3if0003.tasktracker.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.d3if0003.tasktracker.R

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("Title")
        val description = intent.getStringExtra("Description")
        val priority = intent.getStringExtra("Priority")
        val image = intent.getStringExtra("Image")

        setContent {
            DetailScreen(title, description, priority, image)
        }
    }
}

@Composable
fun DetailScreen(title: String?, description: String?, priority: String?, image: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = image ?: R.drawable.baseline_image_search_24),
            contentDescription = "Task Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title ?: "No Title",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description ?: "No Description",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Priority: ${priority ?: "N/A"}",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
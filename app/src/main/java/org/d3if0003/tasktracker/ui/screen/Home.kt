package org.d3if0003.tasktracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.d3if0003.tasktracker.R
import org.d3if0003.tasktracker.model.DataClass
import org.d3if0003.tasktracker.ui.components.DataItemCard

@Composable
fun Home(
    tasks: List<DataClass>,
    onProfileClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onFabClick: () -> Unit,
    onSignInClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteTask: (DataClass) -> Unit,
    onTaskClick: (DataClass) -> Unit,
    isSignedIn: Boolean,
    userName: String?,
    userEmail: String?,
    isLoading: Boolean
) {
    var showUserProfile by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.baseline_face_24),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp)
                        .clickable { showUserProfile = !showUserProfile }
                )
                Text("Klik profile untuk melihat nama, email, dan logout") // Add this line
            }

            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearchQueryChanged(it)
                },
                placeholder = { Text(text = "Search...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .background(Color(0xFFCCCCFF)) // Lavender color
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                ) {
                    items(tasks) { task ->
                        DataItemCard(task, onDeleteTask, onTaskClick) // Pass the task click callback
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(40.dp),
            containerColor = Color(0xFF7070E4) // Lavender color
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24),
                contentDescription = "Add",
                tint = Color.White
            )
        }

        if (showUserProfile) {
            AlertDialog(
                onDismissRequest = { showUserProfile = false },
                title = { Text(text = "User Profile") },
                text = {
                    Column {
                        Text(text = "Name: ${userName ?: "N/A"}")
                        Text(text = "Email: ${userEmail ?: "N/A"}")
                    }
                },
                confirmButton = {
                    Button(onClick = { showUserProfile = false }) {
                        Text("Close")
                    }
                },
                dismissButton = {
                    if (isSignedIn) {
                        Button(onClick = {
                            showUserProfile = false
                            onSignOutClick()
                        }) {
                            Text("Sign Out")
                        }
                    } else {
                        Button(onClick = {
                            showUserProfile = false
                            onSignInClick()
                        }) {
                            Text("Sign In")
                        }
                    }
                }
            )
        }
    }
}

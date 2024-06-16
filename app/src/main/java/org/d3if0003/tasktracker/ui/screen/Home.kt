package org.d3if0003.tasktracker.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
    isLoading: Boolean,
    isNetworkAvailable: Boolean // Add this parameter
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
                Text("Klik profile image untuk info user")
                Spacer(modifier = Modifier.weight(1f))
                if (isSignedIn) {
                    Text(
                        text = "Sign Out",
                        modifier = Modifier.clickable(onClick = onSignOutClick),
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "Sign In",
                        modifier = Modifier.clickable(onClick = onSignInClick),
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (showUserProfile) {
                Text("User: $userName\nEmail: $userEmail")
            }
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    onSearchQueryChanged(it)
                },
                label = { Text("Search Tasks") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(tasks) { task ->
                        DataItemCard(
                            data = task,
                            onDeleteClick = { onDeleteTask(task) },
                            onClick = { onTaskClick(task) } // Handle task click
                        )
                    }
                }
            }
        }

        if (isNetworkAvailable) {
            FloatingActionButton(
                onClick = onFabClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Upload Task")
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_signal_wifi_connected_no_internet_4_24),
                    contentDescription = "No Internet Connection",
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "No internet connection.",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

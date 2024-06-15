package org.d3if0003.tasktracker.ui.screen

import androidx.compose.runtime.Composable
import org.d3if0003.tasktracker.model.DataClass

@Composable
fun MyApp(
    tasks: List<DataClass>,
    onSignOut: () -> Unit,
    onFabClick: () -> Unit,
    onProfileImageClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onDeleteTask: (DataClass) -> Unit,
    onTaskClick: (DataClass) -> Unit, // Add task click callback
    userName: String,
    userEmail: String,
    isLoading: Boolean
) {
    Home(
        tasks = tasks,
        onProfileClick = onProfileImageClick,
        onSearchQueryChanged = onSearchQueryChanged,
        onFabClick = onFabClick,
        onSignInClick = onSignInClick,
        onSignOutClick = onSignOut,
        onDeleteTask = onDeleteTask,
        onTaskClick = onTaskClick, // Pass task click callback
        isSignedIn = true, // Adjust as necessary
        userName = userName,
        userEmail = userEmail,
        isLoading = isLoading
    )
}

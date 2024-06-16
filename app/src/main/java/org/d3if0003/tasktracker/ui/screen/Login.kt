package org.d3if0003.tasktracker.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.common.SignInButton

@Composable
fun Login(onSignInClick: () -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                factory = { context ->
                    SignInButton(context).apply {
                        setOnClickListener {
                            onSignInClick()
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NoInternetConnection() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Replace with your icon if available
            // Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(64.dp))
            Text(text = "No Internet Connection")
            Text(text = "Please connect to the internet to continue")
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun LoginScreenPreview() {
    Login(onSignInClick = {})
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun NoInternetConnectionPreview() {
    NoInternetConnection()
}

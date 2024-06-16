package org.d3if0003.tasktracker.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if0003.tasktracker.R
import org.d3if0003.tasktracker.ui.components.AppNameHeader
import org.d3if0003.tasktracker.ui.components.StyledTextField

@Composable
fun Upload(
    lavender: Color,
    title: String,
    description: String,
    purpose: String,
    isUploading: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPurposeChange: (String) -> Unit,
    onUploadClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSelectImageClick: () -> Unit,
    imageBitmap: Bitmap?,
    isNetworkAvailable: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AppNameHeader(lavender)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (isNetworkAvailable) {
                    UploadCard(
                        lavender = lavender,
                        title = title,
                        description = description,
                        purpose = purpose,
                        onTitleChange = onTitleChange,
                        onDescriptionChange = onDescriptionChange,
                        onPurposeChange = onPurposeChange,
                        onUploadClick = onUploadClick,
                        onCancelClick = onCancelClick,
                        onSelectImageClick = onSelectImageClick,
                        imageBitmap = imageBitmap
                    )
                } else {
                    NoInternetCard(lavender)
                }
            }
        }

        if (isUploading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun UploadCard(
    lavender: Color,
    title: String,
    description: String,
    purpose: String,
    imageBitmap: Bitmap?,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPurposeChange: (String) -> Unit,
    onUploadClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSelectImageClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(15.dp)
                .clip(RectangleShape)
        ) {
            Text(
                text = "UPLOAD NEW Image!",
                color = lavender,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageBitmap != null) {
                    Image(
                        bitmap = imageBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_image_search_24),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )
                }
            }

            StyledTextField(
                value = title,
                onValueChange = onTitleChange,
                placeholder = "Enter Image name",
                lavender = lavender
            )

            StyledTextField(
                value = description,
                onValueChange = onDescriptionChange,
                placeholder = "Enter Image Description",
                lavender = lavender
            )

            StyledTextField(
                value = purpose,
                onValueChange = onPurposeChange,
                placeholder = "Enter Image Purpose",
                lavender = lavender
            )

            Button(
                onClick = onSelectImageClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text(
                    text = "Select Image",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = onUploadClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text(
                    text = "UPLOAD",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = onCancelClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Text(
                    text = "CANCEL",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun NoInternetCard(lavender: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 20.dp, end = 20.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 20.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(15.dp)
                .clip(RectangleShape),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_signal_wifi_connected_no_internet_4_24),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
            )
            Text(
                text = "No Internet Connection",
                color = lavender,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadCardPreview() {
    UploadCard(
        lavender = Color(0xFFB388FF),
        title = "",
        description = "",
        purpose = "",
        imageBitmap = null,
        onTitleChange = {},
        onDescriptionChange = {},
        onPurposeChange = {},
        onUploadClick = {},
        onCancelClick = {},
        onSelectImageClick = {}
    )
}
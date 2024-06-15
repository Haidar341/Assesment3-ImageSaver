package org.d3if0003.tasktracker.ui.screen

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3if0003.tasktracker.ui.components.AppNameHeader
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.d3if0003.tasktracker.R

@Composable
fun Upload(
    lavender: Color,
    title: String,
    description: String,
    priority: String,
    isUploading: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    onUploadClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSelectImageClick: () -> Unit,
    imageBitmap: Bitmap?
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
                UploadCard(
                    lavender = lavender,
                    title = title,
                    description = description,
                    priority = priority,
                    onTitleChange = onTitleChange,
                    onDescriptionChange = onDescriptionChange,
                    onPriorityChange = onPriorityChange,
                    onUploadClick = onUploadClick,
                    onCancelClick = onCancelClick,
                    onSelectImageClick = onSelectImageClick,
                    imageBitmap = imageBitmap
                )
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
    priority: String,
    imageBitmap: Bitmap?, // Add a parameter to accept the selected image
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    onUploadClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSelectImageClick: () -> Unit // Add a parameter for image selection
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
                value = priority,
                onValueChange = onPriorityChange,
                placeholder = "Enter Image Priority",
                lavender = lavender
            )

            Button(
                onClick = onSelectImageClick, // Set the image selection button
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
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    lavender: Color
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = placeholder,
                color = lavender,
                fontSize = 15.sp
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                color = lavender,
                fontSize = 15.sp
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clip(RoundedCornerShape(15.dp))
    )
}

@Preview(showBackground = true)
@Composable
fun UploadCardPreview() {
    UploadCard(
        lavender = Color(0xFFB388FF),
        title = "",
        description = "",
        priority = "",
        imageBitmap = null,
        onTitleChange = {},
        onDescriptionChange = {},
        onPriorityChange = {},
        onUploadClick = {},
        onCancelClick = {},
        onSelectImageClick = {}
    )
}

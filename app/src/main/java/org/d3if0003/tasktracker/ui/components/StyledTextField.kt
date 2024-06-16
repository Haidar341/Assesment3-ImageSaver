package org.d3if0003.tasktracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
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
        placeholder = {
            Text(
                text = placeholder,
                color = lavender.copy(alpha = 0.5f)
            )
        },
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = lavender,
            unfocusedBorderColor = lavender.copy(alpha = 0.5f)
        )
    )
}
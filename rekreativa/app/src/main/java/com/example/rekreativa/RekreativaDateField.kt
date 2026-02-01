package com.example.rekreativa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RekreativaDateField(
    value: String,
    label: String = "Datum rođenja",
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .shadow(
            elevation = 24.dp,
            shape = RoundedCornerShape(24.dp),
            clip = false
        )) {

        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            leadingIcon = { Icon(Icons.Filled.DateRange, contentDescription = null) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF0B4CFF),
                unfocusedBorderColor = Color(0xFFB0B0B0),

                // POZADINA
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,

                // TEKST U POLJU
                focusedTextColor = Color(0xFF0B4CFF),
                unfocusedTextColor = Color(0xFF111827),

                // LABEL
                focusedLabelColor = Color(0xFF0B4CFF),
                unfocusedLabelColor = Color(0xFF6B7280),

                // IKONE
                focusedLeadingIconColor = Color(0xFF0B4CFF),
                unfocusedLeadingIconColor = Color(0xFF6B7280),
                focusedTrailingIconColor = Color(0xFF6B7280),
                unfocusedTrailingIconColor = Color(0xFF6B7280),

                // KURSOR
                cursorColor = Color(0xFF0B4CFF)
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable { onClick() }
        )
    }
}

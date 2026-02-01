package com.example.rekreativa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBlue
import com.example.rekreativa.ui.theme.RekreativaBorderFocused
import com.example.rekreativa.ui.theme.RekreativaBorderUnfocused
import com.example.rekreativa.ui.theme.RekreativaCursorColor
import com.example.rekreativa.ui.theme.RekreativaTextFocused
import com.example.rekreativa.ui.theme.RekreativaTextUnfocused

@Composable
fun SearchBarRekreativa(
    text: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(0.87f)
                .shadow(8.dp, RoundedCornerShape(26.dp), clip = false),
            singleLine = true,
            shape = RoundedCornerShape(26.dp),
            placeholder = { Text(text, color = Color(0xFF9CA3AF), fontSize = 13.sp) },
            trailingIcon = {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Search,
                    contentDescription = null,
                    tint = RekreativaBlue
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = RekreativaBorderFocused,
                unfocusedBorderColor = RekreativaBorderUnfocused,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = RekreativaTextFocused,
                unfocusedTextColor = RekreativaTextUnfocused,
                cursorColor = RekreativaCursorColor
            )
        )
    }
}
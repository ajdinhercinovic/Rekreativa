package com.example.rekreativa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBorderFocused
import com.example.rekreativa.ui.theme.RekreativaBorderUnfocused
import com.example.rekreativa.ui.theme.RekreativaTextFocused
import com.example.rekreativa.ui.theme.RekreativaTextGray

@Composable fun RekreativaTogglePill(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderSize = if (selected) 2.dp else 1.dp
    val bg = Color.White
    val border = if (selected) RekreativaBorderFocused else RekreativaBorderUnfocused
    val txt = if (selected) RekreativaTextFocused else RekreativaTextGray

    Surface(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        color = bg,
        tonalElevation = 0.dp,
        shadowElevation = 8.dp,
        border = BorderStroke(borderSize, border)
    ) {
        Text(
            text = text,
            color = txt,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
        )
    }
}
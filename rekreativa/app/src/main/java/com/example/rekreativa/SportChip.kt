package com.example.rekreativa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBorderFocused
import com.example.rekreativa.ui.theme.RekreativaBorderUnfocused

@Composable
fun SportChip(
    sport: Sport,
    selected: Boolean,
    onToggle: () -> Unit
) {
    val borderColor = if (selected) RekreativaBorderFocused else RekreativaBorderUnfocused
    val width = if (selected) 2.2.dp else 1.2.dp
    val bg = if (selected) Color(0xFFEAF1FF) else Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(66.dp)
            .clickable { onToggle() }
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .shadow(6.dp, CircleShape, clip = false)
                .clip(CircleShape)
                .background(bg)
                .border(width = width, color = borderColor, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = sport.iconRes),
                contentDescription = null,
                modifier = Modifier.size(26.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = sport.naziv.uppercase(),
            fontSize = 9.sp,
            color = Color(0xFF6B7280),
            fontWeight = FontWeight.SemiBold
        )
    }
}
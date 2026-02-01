package com.example.rekreativa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RekreativaHeaderText(
    text: String
){
    Spacer(Modifier.height(30.dp))

    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(0xFF6B6B6B),
        letterSpacing = 1.sp,
        style = TextStyle(
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.4f),
                offset = Offset(0f, 2f),
                blurRadius = 8f
            )
        )
    )

    Spacer(Modifier.height(8.dp))

    //Plava linija
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth((Alignment.CenterHorizontally))
            .width(320.dp)
            .height(3.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFF3C67BF))
    )
}
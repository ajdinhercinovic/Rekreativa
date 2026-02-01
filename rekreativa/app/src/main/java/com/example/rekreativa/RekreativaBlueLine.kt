package com.example.rekreativa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.rekreativa.ui.theme.RekreativaBlue

@Composable
fun RekreativaBlueLine(){
    Spacer(Modifier.height(8.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth((Alignment.CenterHorizontally))
            .width(320.dp)
            .height(3.dp)
            .clip(RoundedCornerShape(50))
            .background(RekreativaBlue)
    )
}
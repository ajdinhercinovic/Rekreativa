package com.example.rekreativa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBlue

@Composable
fun ReservationCard(
    r: Reservation,
    onConfirm: (String) -> Unit,
    onDelete: (String) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6DBE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text("TEREN:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text(r.terenName, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF111827))

            Spacer(Modifier.height(8.dp))

            Text("TERMIN:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text("${r.dayOfWeek} ${r.date} (${r.timeFrom} - ${r.timeTo})", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = RekreativaBlue)

            Spacer(Modifier.height(8.dp))

            Text("TIP: ${r.tip_rezervacije} • STATUS: ${r.status}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RekreativaImageButton("OTKAŽI",
                    onClick = { onDelete(r.id) },
                    modifier = Modifier
                        .height(32.dp)
                        .width(120.dp),
                    textSize = 12.sp
                )
                RekreativaImageButton(
                    "POTVRDI",
                    onClick = { onConfirm(r.id) },
                    modifier = Modifier
                        .height(32.dp)
                        .width(120.dp),
                    textSize = 12.sp
                )
            }
        }
    }
}
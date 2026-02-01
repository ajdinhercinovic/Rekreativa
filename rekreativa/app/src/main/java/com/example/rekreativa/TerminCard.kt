package com.example.rekreativa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBlue

@Composable
fun TerminCard(
    termin: Termin,
    onSettings: () -> Unit
) {
    val teren = RekreativaData.terenById(termin.terenId)
    val sport = RekreativaData.sportById(termin.sportId)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6DBE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(
                    text = "${termin.tip.uppercase()} korisnika ",
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Spacer(Modifier.width(2.dp))
                if (termin.creator_avatar != null) {
                    Image(
                        painter = painterResource(id = termin.creator_avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFFFFF)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = termin.creator,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                    )
                }
            }
            Text("TERMIN:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text(
                "${termin.datumLabel} (${termin.vrijemeLabel})",
                fontSize = 14.sp,
                color = RekreativaBlue,
                fontWeight = FontWeight.ExtraBold
            )
            Text("SPORT:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (sport?.iconRes != null) {
                    Image(
                        painter = painterResource(id = sport.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    (sport?.naziv ?: "Sport").uppercase(),
                    fontSize = 13.sp,
                    color = RekreativaBlue,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {

                    Spacer(Modifier.height(6.dp))
                    Text("TEREN:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
                    Text(
                        teren?.naziv ?: "Nepoznata lokacija",
                        fontSize = 13.sp,
                        color = Color(0xFF111827),
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                if (termin.isCreator) {
                    IconButton(
                        onClick = onSettings,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pozadina_h),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Postavke termina",
                            tint = Color(0xFFF3E5E5)
                        )
                    }
                }
            }

        }
    }
}
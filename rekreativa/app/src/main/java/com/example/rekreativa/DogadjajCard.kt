package com.example.rekreativa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.RekreativaData.terenById
import com.example.rekreativa.ui.theme.RekreativaBlue
import com.example.rekreativa.ui.theme.RekreativaTextGray

@Composable
fun DogadjajCard(
    dogadjaj: Dogadjaj,
    terenNaziv: String,
    sportNaziv: String,
    sportIconRes: Int?,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6DBE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // TIP + SPORT
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "VRSTA DOGAĐAJA:",
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dogadjaj.tip.name,
                        fontSize = 18.sp,
                        color = Color(0xFF111827),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "SPORT:",
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (sportIconRes != null) {
                            Image(
                                painter = painterResource(id = sportIconRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                        }
                        Text(
                            text = sportNaziv.uppercase(),
                            fontSize = 14.sp,
                            color = RekreativaBlue,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "ORGANIZATOR:",
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (dogadjaj.organizatorAvatarRes != null) {
                            Image(
                                painter = painterResource(id = dogadjaj.organizatorAvatarRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFFFFFFF)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                        Text(
                            text = dogadjaj.organizatorIme,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827),
                            modifier = Modifier.widthIn(max = 140.dp)
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .height(90.dp)
                            .width(150.dp)
                    ){
                        val teren = terenById(dogadjaj.lokacijaTerenId)
                        Image(
                            painter = painterResource(id = teren!!.slikaRes),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
            }

            Text(
                text = "LOKACIJA:",
                fontSize = 11.sp,
                color = Color(0xFF6B7280),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = terenNaziv,
                fontSize = 12.sp,
                color = Color(0xFF111827),
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "TERMIN:",
                fontSize = 11.sp,
                color = RekreativaTextGray,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${dogadjaj.datumLabel} (${dogadjaj.vrijemeLabel})",
                fontSize = 12.sp,
                color = RekreativaBlue,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "BROJ SLOBODNIH MJESTA: ${dogadjaj.slobodnihMjesta}",
                    fontSize = 11.sp,
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.Bold
                )

                val btnText = if (dogadjaj.pristup == PristupTip.OTVOREN) "PRIDRUŽI SE" else "POŠALJI ZAHTJEV"

                RekreativaImageButton(
                    onClick = onAction,
                    text = btnText,
                    textSize = 11.sp,
                    modifier = Modifier
                        .width(140.dp)
                        .height(36.dp)
                )
            }
            Spacer(Modifier.height(4.dp))
        }
    }
}
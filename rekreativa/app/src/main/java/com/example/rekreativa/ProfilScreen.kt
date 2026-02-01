package com.example.rekreativa

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBlue

@Composable
fun ProfilScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EEF3)),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { RekreativaHeader() }

        item {
            RekreativaHeaderText("MOJ PROFIL")
        }

        item {
            Spacer(Modifier.height(20.dp))
            ProfileInfoCard(
                user = RekreativaData.currentUser,
                sports = RekreativaData.mySports,
                onEditProfile = {}
            )
            Spacer(Modifier.height(14.dp))
        }

        item {
            RatingStatsCard(stats = RekreativaData.myStats)
            Spacer(Modifier.height(14.dp))
        }
    }
}

@Composable
fun ProfileInfoCard(
    user: User,
    sports: List<Sport?>,
    onEditProfile: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6DBE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Text(
                text = "LIČNI PODACI",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6B7280)
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Spacer(Modifier.width(20.dp))
                Image(
                    painter = painterResource(id = user.avatarRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "IME I PREZIME",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6B7280)
                    )
                    Text(
                        text = "${user.ime} ${user.prezime}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                InfoField(label = "USERNAME", "@${user.username}", modifier = Modifier.weight(1f))
                Spacer(Modifier.width(10.dp))
                InfoField(label = "BROJ MOBITELA", value = user.telefon, modifier = Modifier.weight(1f))
                Spacer(Modifier.width(10.dp))
                InfoField(label = "DATUM ROĐENJA", value = user.datumRodjenja, modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = "MOJI SPORTOVI",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6B7280)
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                sports.take(5).forEach { sport ->
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = sport!!.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                }
            }

            Spacer(Modifier.height(20.dp))

            RekreativaImageButton(
                text = "UREDI PROFIL",
                onClick = onEditProfile,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(180.dp)
                    .height(36.dp)
            )
        }
        Spacer(Modifier.height(14.dp))
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RatingStatsCard(stats: List<SportStat>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD6DBE0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Text(
                text = "REJTING I STATISTIKA",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6B7280)
            )

            Spacer(Modifier.height(12.dp))

            // Header row
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("SPORT", modifier = Modifier.weight(0.5f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
                Text("BROJ TERMINA", modifier = Modifier.weight(1.2f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280), textAlign = TextAlign.Center)
                Text("FAIR PLAY", modifier = Modifier.weight(1f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280), textAlign = TextAlign.Center)
                Text("ZNANJE", modifier = Modifier.weight(1f), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280), textAlign = TextAlign.Center)
            }

            Spacer(Modifier.height(10.dp))

            stats.forEach { s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Image(
                                painter = painterResource(id = s.sportIconRes),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = s.brojTermina.toString(),
                        modifier = Modifier.weight(1.2f),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = RekreativaBlue
                    )

                    Text(
                        text = String.format("%.1f", s.fairPlay),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ratingColor(s.fairPlay)
                    )

                    Text(
                        text = String.format("%.1f", s.znanje),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = ratingColor(s.znanje)
                    )
                }

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFB0B0B0))
            }
        }
    }
}

@Composable
private fun InfoField(label: String, value: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
        Spacer(Modifier.height(2.dp))
        Text(value, fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF111827))
    }
}

private fun ratingColor(value: Double): Color {
    return when {
        value >= 4.0 -> Color(0xFF16A34A)
        value >= 3.0 -> Color(0xFFF59E0B)
        else -> Color(0xFFDC2626)
    }
}
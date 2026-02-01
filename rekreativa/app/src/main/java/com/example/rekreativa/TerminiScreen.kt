package com.example.rekreativa

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBlue
import com.example.rekreativa.ui.theme.RekreativaBorderFocused
import com.example.rekreativa.ui.theme.RekreativaBorderUnfocused
import com.example.rekreativa.ui.theme.RekreativaTextFocused
import com.example.rekreativa.ui.theme.RekreativaTextGray
import kotlinx.coroutines.launch

@Composable
fun TerminiScreen() {

    val repo = remember { ReservationsRepository() }
    val scope = rememberCoroutineScope()
    val userId = RekreativaData.currentUser.username

    var reservations by remember { mutableStateOf<List<Reservation>>(emptyList()) }

    LaunchedEffect(Unit) {
        reservations = repo.getReservationsForUser(userId)
    }

    val pending = reservations.filter { it.status == "PENDING" }
    val confirmed = reservations.filter { it.status == "CONFIRMED" }

    var filter by remember { mutableStateOf(TerminiFilter.SVE) }

    val terminiAll = RekreativaData.termini
    val poziviAll = RekreativaData.pozivi

    val termini = remember(terminiAll) { terminiAll }
    val primljeniPozivi = remember(poziviAll) { poziviAll.filter { it.tip == PozivTip.PRIMLJEN } }
    val poslaniPozivi = remember(poziviAll) { poziviAll.filter { it.tip == PozivTip.POSLAN } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EEF3)),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { RekreativaHeader() }

        item{RekreativaHeaderText("MOJI TERMINI I POZIVI")}
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TogglePill3("SVE", filter == TerminiFilter.SVE) { filter = TerminiFilter.SVE }
                Spacer(Modifier.width(10.dp))
                TogglePill3("TERMINI", filter == TerminiFilter.TERMINI) {
                    filter = TerminiFilter.TERMINI
                }
                Spacer(Modifier.width(10.dp))
                TogglePill3("POZIVI", filter == TerminiFilter.POZIVI) {
                    filter = TerminiFilter.POZIVI
                }
            }
            Spacer(Modifier.height(6.dp))
            RekreativaBlueLine()
        }

        item { RekreativaTitle("REZERVACIJE") }

        if (pending.isEmpty()) {
            item { EmptyStateCard("Nema rezervacija na čekanju", "Sve rezervacije će Vam se prikazati ovdje.") }
        } else {
            items(pending, key = { it.id }) { r ->
                ReservationCard(
                    r = r,
                    onConfirm = { id ->
                        scope.launch {
                            repo.updateStatus(id, "CONFIRMED")
                            reservations = repo.getReservationsForUser(userId)
                        }
                    },
                    onDelete = { id ->
                        scope.launch {
                            repo.deleteReservation(id)
                            reservations = repo.getReservationsForUser(userId)
                        }
                    }
                )
            }
        }

        if (filter == TerminiFilter.SVE || filter == TerminiFilter.TERMINI) {
            item {
                RekreativaTitle("DOGOVORENI TERMINI")
            }

            if (confirmed.isEmpty()) {
                item {
                    EmptyStateCard(
                        title = "Nema dogovorenih termina",
                        subtitle = "Rezerviši teren ili se pridruži događaju."
                    )
                    Spacer(Modifier.height(14.dp))
                }
            } else {
                items(confirmed, key = { it.id }) { r ->
                    TerminCard(
                        termin = r.toTermin(),
                        onSettings ={}
                    )
                }
                item { Spacer(Modifier.height(14.dp)) }
            }
        }

        if (filter == TerminiFilter.SVE || filter == TerminiFilter.POZIVI) {

            // PRIMLJENI
            item {
                RekreativaTitle("PRIMLJENI POZIVI")
                Spacer(Modifier.height(10.dp))
            }

            if (primljeniPozivi.isEmpty()) {
                item {
                    EmptyStateCard(
                        title = "Nema primljenih poziva",
                        subtitle = "Kada te neko pozove, poziv će biti prikazan ovdje."
                    )
                    Spacer(Modifier.height(14.dp))
                }
            } else {
                items(primljeniPozivi, key = { it.id }) { poziv ->
                    PozivCardPrimljen(poziv = poziv, onAccept = {}, onDecline = {})
                }
                item { Spacer(Modifier.height(14.dp)) }
            }

            // POSLANI
            item {
                RekreativaTitle("POSLANI POZIVI")
                Spacer(Modifier.height(10.dp))
            }

            if (poslaniPozivi.isEmpty()) {
                item {
                    EmptyStateCard(
                        title = "Nema poslanih poziva",
                        subtitle = "Pozovi korisnika na termin i vidjet ćeš evidenciju ovdje."
                    )
                    Spacer(Modifier.height(14.dp))
                }
            } else {
                items(poslaniPozivi, key = { it.id }) { poziv ->
                    PozivCardPoslan(poziv = poziv)
                }
                item { Spacer(Modifier.height(14.dp)) }
            }
        }

        item { Spacer(Modifier.height(10.dp)) }
    }
}

@Composable
private fun TogglePill3(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val border_size = if (selected) 2.dp else 1.dp
    val bg = Color.White
    val border = if (selected) RekreativaBorderFocused else RekreativaBorderUnfocused
    val txt = if (selected) RekreativaTextFocused else RekreativaTextGray

    Surface(
        onClick = onClick,
        modifier = Modifier.height(36.dp),
        shape = RoundedCornerShape(18.dp),
        color = bg,
        tonalElevation = 0.dp,
        shadowElevation = 8.dp,
        border = BorderStroke(border_size, border)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 6.dp),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.sp,
            color = txt
        )
    }
}

@Composable
private fun PozivCardPrimljen(
    poziv: Poziv,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    val teren = RekreativaData.terenById(poziv.terenId)
    val sport = RekreativaData.sportById(poziv.sportId)

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text ="DOBILI STE POZIV OD",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
                Spacer(Modifier.width(4.dp))
                if (poziv.korisnikAvatarRes != null) {
                    Image(
                        painter = painterResource(id = poziv.korisnikAvatarRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF111827)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    poziv.korisnikIme,
                    fontSize = 14.sp,
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(10.dp))

            Text("LOKACIJA:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text(
                teren?.naziv ?: "Nepoznata lokacija",
                fontSize = 12.sp,
                color = Color(0xFF111827),
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(6.dp))

            Text("SPORT:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (sport?.iconRes != null) {
                    Image(
                        painter = painterResource(id = sport.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    (sport?.naziv ?: "Sport").uppercase(),
                    fontSize = 12.sp,
                    color = RekreativaBlue,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(8.dp))

            Text("TERMIN:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text(
                "${poziv.datumLabel} (${poziv.vrijemeLabel})",
                fontSize = 12.sp,
                color = RekreativaBlue,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    IconButton(onClick = onDecline,
                        modifier = Modifier
                            .height(20.dp)
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "Odbij",
                            tint = Color(0xFFDC2626),
                        )
                    }
                    Text(
                        text = "ODBIJ",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFDC2626),
                    )
                }
                Spacer(Modifier.width(50.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = onAccept,
                        modifier = Modifier
                            .height(20.dp)
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Prihvati",
                            tint = Color(0xFF16A34A)
                        )
                    }
                    Text(
                        text = "PRIHVATI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF16A34A),
                    )
                }
            }
        }
    }
}

@Composable
private fun PozivCardPoslan(poziv: Poziv) {
    val teren = RekreativaData.terenById(poziv.terenId)
    val sport = RekreativaData.sportById(poziv.sportId)

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text ="POSLAN POZIV KORISINIKU",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
                Spacer(Modifier.width(4.dp))
                if (poziv.korisnikAvatarRes != null) {
                    Image(
                        painter = painterResource(id = poziv.korisnikAvatarRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF111827)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    poziv.korisnikIme,
                    fontSize = 14.sp,
                    color = Color(0xFF111827),
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(10.dp))

            Text("LOKACIJA:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text(
                teren?.naziv ?: "Nepoznata lokacija",
                fontSize = 12.sp,
                color = Color(0xFF111827),
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(6.dp))

            Text("SPORT:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (sport?.iconRes != null) {
                    Image(
                        painter = painterResource(id = sport.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                }
                Text(
                    (sport?.naziv ?: "Sport").uppercase(),
                    fontSize = 12.sp,
                    color = RekreativaBlue,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(8.dp))

            Text("TERMIN:", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
            Text(
                "${poziv.datumLabel} (${poziv.vrijemeLabel})",
                fontSize = 12.sp,
                color = RekreativaBlue,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "STATUS:",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF6B7280)
            )
            Text(
                "BEZ ODGOVORA",
                fontSize = 12.sp,
                color = Color(0xFF000000),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}


package com.example.rekreativa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBackground

@Composable
fun DogadjajiScreen() {
    val sports = RekreativaData.sports
    val tereni = RekreativaData.tereni
    val dogadjajiAll = RekreativaData.dogadjaji

    var selectedSportId by remember { mutableStateOf<String?>(null) }
    var search by remember { mutableStateOf("") }
    var onlyFriends by remember { mutableStateOf(false) }

    /*val filtered = remember(search, selectedSports, onlyFriends) {
        dogadjajiAll
            .asSequence()
            .filter { selectedSports.isEmpty() || it.sportId in selectedSports }
            .filter { d ->
                if (search.isBlank()) true else {
                    val terenNaziv = tereni.firstOrNull { it.id == d.lokacijaTerenId }?.naziv.orEmpty()
                    val sportNaziv = sports.firstOrNull { it.id == d.sportId }?.naziv.orEmpty()
                    (terenNaziv + " " + sportNaziv + " " + d.organizatorIme)
                        .contains(search, ignoreCase = true)
                }
            }
            .filter { d -> if (!onlyFriends) true else d.organizatorIme != "Rekreativa tim" }
            .toList()
    }*/


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(RekreativaBackground),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            RekreativaHeader()
        }

        item {
            RekreativaHeaderText("PRIDRUŽI SE DOGAĐAJU")
        }

        item {
            Spacer(Modifier.height(14.dp))
            SearchBarRekreativa(
                text = "Pretraži događaje...",
                value = search,
                onValueChange = { search = it }
            )
        }

        item {
            HorizontalSportFilter(
                selectedSportId = selectedSportId,
                onSelectedSportChange = { selectedSportId = it }
            )
        }

        item {
            Text(
                text = "FILTRIRAJ DOGAĐAJE PO KORISNICIMA",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF7A7A7A),
                fontWeight = FontWeight.SemiBold,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                RekreativaTogglePill(
                    text = "SVI",
                    selected = !onlyFriends,
                    onClick = { onlyFriends = false }
                )
                Spacer(Modifier.width(12.dp))

                RekreativaTogglePill(
                    text = "PRIJATELJI",
                    selected = onlyFriends,
                    onClick = { onlyFriends = true }
                )
            }

            Spacer(Modifier.height(20.dp))

            RekreativaBlueLine()
            Spacer(Modifier.height(14.dp))
        }

        /*items(filtered, key = { it.id }) { dogadjaj ->
            val teren = tereni.firstOrNull { it.id == dogadjaj.lokacijaTerenId }
            val sport = sports.firstOrNull { it.id == dogadjaj.sportId }

            DogadjajCard(
                dogadjaj = dogadjaj,
                terenNaziv = teren?.naziv ?: "Nepoznata lokacija",
                sportNaziv = sport?.naziv ?: "Sport",
                sportIconRes = sport?.iconRes,
                onAction = { *//* *//* }
            )
        }*/

        item { Spacer(Modifier.height(16.dp)) }
    }
}


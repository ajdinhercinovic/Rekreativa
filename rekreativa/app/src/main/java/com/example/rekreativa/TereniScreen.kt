package com.example.rekreativa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaBlue

@Composable
fun TereniScreen(
    onOpenTerenDetails: (String) -> Unit = {},
) {
    val sports = RekreativaData.sports
    val tereni = RekreativaData.tereni
    var query by remember { mutableStateOf("") }
    var selectedSports by remember { mutableStateOf(setOf<String>()) }

    val filtered = remember(query, selectedSports, tereni) {
        tereni.filter { t ->
            val matchesQuery = query.isBlank() || t.naziv.contains(query, ignoreCase = true)
            val matchesSports = selectedSports.isEmpty() || t.sportIds.any { it in selectedSports }
            matchesQuery && matchesSports
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EEF3)),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            RekreativaHeader()
        }

        item {
            RekreativaHeaderText("REZERVIŠI TEREN ILI SALU")
        }

        item {
            Spacer(Modifier.height(14.dp))

            SearchBarRekreativa(
                text = "Pretraži terene...",
                value = query,
                onValueChange = { query = it }
            )
        }

        item {
            HorizontalSportFilter(
                selectedSports = selectedSports,
                onSelectedSportsChange = { selectedSports = it }
            )
        }

        items(filtered, key = {it.id}) { teren->
            TerenCard(
                teren = teren,
                sportIcons = sports
                    .filter { it.id in teren.sportIds }
                    .map { it.iconRes },
                onOpen = { onOpenTerenDetails(teren.id) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}
@Composable
private fun TerenCard(
    teren: Teren,
    sportIcons: List<Int>,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .shadow(12.dp, RoundedCornerShape(18.dp), clip = false),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xff0b1020))
                    .padding(vertical = 10.dp)
            ){
                Text(
                    text = teren.naziv,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Image(
                painter = painterResource(id = teren.slikaRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    sportIcons.forEach {iconRes ->
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .shadow(6.dp, CircleShape, clip = false)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(Modifier.width(6.dp))

                    //Osobe
                    Box(
                        modifier = Modifier
                            .height(34.dp)
                            .shadow(6.dp, RoundedCornerShape(14.dp), clip = false)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White)
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Groups,
                                contentDescription = null,
                                tint = RekreativaBlue,
                                modifier = Modifier.size(22.dp)
                            )

                            Spacer(Modifier.width(3.dp))

                            Text(
                                text = teren.preporucenoOsoba,
                                color = RekreativaBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                RekreativaImageButton(
                    text = "POGLEDAJ",
                    textSize = 12.sp,
                    onClick = onOpen,
                    modifier = Modifier
                        .width(100.dp)
                        .height(34.dp)
                )
            }
        }
    }
}

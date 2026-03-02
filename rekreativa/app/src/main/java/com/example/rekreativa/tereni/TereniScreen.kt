package com.example.rekreativa.tereni

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.SearchOff
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
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.RekreativaData
import com.example.rekreativa.RekreativaHeader
import com.example.rekreativa.RekreativaHeaderText
import com.example.rekreativa.RekreativaImageButton
import com.example.rekreativa.SearchBarRekreativa
import com.example.rekreativa.data.TereniViewModel
import com.example.rekreativa.ui.theme.RekreativaBlue
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.rekreativa.Field
import com.example.rekreativa.HorizontalSportFilter
import com.example.rekreativa.ui.theme.RekreativaTextGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TereniScreen(
    onOpenTerenDetails: (String) -> Unit = {},
    viewModel: TereniViewModel = viewModel(
        factory = TereniViewModelFactory()
    )
) {
    val sports = RekreativaData.sports
    val ui by viewModel.state.collectAsState()

    var query by remember { mutableStateOf("") }
    var selectedSportId by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(selectedSportId) {
        viewModel.setSportFilter(selectedSportId)
    }

    val filtered = remember(query, ui.fields) {
        ui.fields.filter { t ->
            query.isBlank() || t.name.contains(query, ignoreCase = true)
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showCitySheet by remember { mutableStateOf(false) }

    if (showCitySheet) {
        ModalBottomSheet(
            onDismissRequest = { showCitySheet = false },
            sheetState = sheetState
        ) {
            CityPickerSheet(
                selected = ui.city,
                onSelect = { city ->
                    viewModel.setCity(city)
                    showCitySheet = false
                }
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EEF3)),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item { RekreativaHeader() }

        item { RekreativaHeaderText("REZERVIŠI TEREN ILI SALU") }

        item {
            CityBar(
                city = ui.city,
                onClick = { showCitySheet = true }
            )
        }

        item {
            SearchBarRekreativa(
                text = "Pretraži terene...",
                value = query,
                onValueChange = { query = it }
            )
        }

        item {
            HorizontalSportFilter(
                selectedSportId = selectedSportId,
                onSelectedSportChange = { selectedSportId = it }
            )
        }

        when {
            ui.isLoading -> {
                item { LoadingBlock() } // napravi mali loader composable
            }
            ui.city == null -> {
                item {
                    NoCityBlock(
                        onPickCity = { showCitySheet = true }
                    )
                }
            }
            ui.error != null -> {
                item {
                    ErrorBlock(
                        message = ui.error!!,
                        onRetry = { viewModel.setCity(ui.city!!) } // simplest retry (ponovo setuje isti grad)
                    )
                }
            }
            filtered.isEmpty() -> {
                item { EmptyBlock() }
            }
            else -> {
                items(filtered, key = { it.id }) { teren ->
                    TerenCard(
                        teren = teren,
                        sportIcons = sports.filter { it.id in teren.sportIds }.map { it.iconRes },
                        onOpen = { onOpenTerenDetails(teren.id) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }

    LaunchedEffect(ui.city) {
        if (ui.city == null && !ui.isLoading) showCitySheet = true
    }
}
@Composable
private fun TerenCard(
    teren: Field,
    sportIcons: List<Int>,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.shadow(12.dp, RoundedCornerShape(18.dp), clip = false),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xff0b1020))
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = teren.name.toUpperCase(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            AsyncImage(
                model = teren.imageUrl,
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
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    sportIcons.forEach { iconRes ->
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
                                text = teren.recommendedPeople,
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
                    modifier = Modifier.width(100.dp).height(34.dp)
                )
            }
        }
    }
}

@Composable
private fun CityBar(city: String?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Filled.LocationOn, contentDescription = null, tint = RekreativaBlue)

        Text(
            text = city?.toUpperCase() ?: "Odaberi grad",
            fontWeight = FontWeight.Bold,
            color = RekreativaTextGray,
            fontSize = 20.sp
        )
        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null, tint = RekreativaTextGray)
    }
}

@Composable
private fun CityPickerSheet(
    selected: String?,
    onSelect: (String) -> Unit
) {
    val cities = listOf(
        "Sarajevo","Banja Luka","Tuzla","Zenica","Mostar","Bihać","Bijeljina","Brčko","Travnik","Doboj"
    )

    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Odaberi grad", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))

        cities.forEach { city ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (city == selected) RekreativaBlue else Color.Transparent)
                    .clickable { onSelect(city) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(city, fontWeight = if (city == selected) FontWeight.Bold else FontWeight.Normal)
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
fun LoadingBlock() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = RekreativaBlue
        )
    }
}

@Composable
fun EmptyBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(60.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Nema dostupnih terena za odabrane filtere.",
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

@Composable
fun ErrorBlock(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(60.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Pokušaj ponovo")
        }
    }
}

@Composable
fun NoCityBlock(
    onPickCity: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = RekreativaBlue,
            modifier = Modifier.size(60.dp)
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Prvo odaberi grad da bi vidio dostupne terene.",
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(16.dp))

        Button(onClick = onPickCity) {
            Text("Odaberi grad")
        }
    }
}
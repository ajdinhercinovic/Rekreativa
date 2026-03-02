package com.example.rekreativa.tereni

import android.annotation.SuppressLint
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Text
import com.example.rekreativa.ui.theme.RekreativaBackground
import com.example.rekreativa.ui.theme.RekreativaBlue
import com.example.rekreativa.ui.theme.RekreativaTextGray
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.rekreativa.RekreativaData
import com.example.rekreativa.RekreativaHeader
import com.example.rekreativa.RekreativaImageButton
import com.example.rekreativa.RekreativaReservationButton
import com.example.rekreativa.RekreativaTogglePill
import com.example.rekreativa.Reservation
import com.example.rekreativa.ReservationsRepository
import com.example.rekreativa.data.TerenDetailsViewModel
import com.example.rekreativa.ui.theme.RekreativaBorderUnfocused
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TerenDetailsScreen(
    terenId: String,
    onBack: () -> Unit,
){
    val viewModel: TerenDetailsViewModel = viewModel(
        factory = TerenDetailsViewModelFactory(terenId)
    )

    val teren by viewModel.state.collectAsState()
    if (teren == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = RekreativaBlue)
        }
        return
    }

    val sports = RekreativaData.sports
    val sportIcons = remember(terenId){
        sports
            .filter { it.id in teren!!.sportIds }
            .map { it.iconRes }
            .take(3)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var isSubmitting by remember { mutableStateOf(false) }

    var isWeekly by remember { mutableStateOf(false) }
    var selectedDay by remember { mutableStateOf("PONEDJELJAK") }
    var selectedPeriod by remember { mutableStateOf("17:30 - 18:30") }
    var eventType by remember { mutableStateOf("REKREACIJA") }
    var isPrivate by remember { mutableStateOf(false)}

    var selectedDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()


    val events = listOf("REKREACIJA", "TRENING", "TURNIR")
    val days = listOf("PONEDJELJAK","UTORAK","SRIJEDA","ČETVRTAK","PETAK","SUOBTA","NEDJELJA")
    val periods = listOf(
        "08:00 - 09:00","09:00 - 10:00","10:00 - 11:00",
        "16:00 - 17:00","17:00 - 18:00","17:30 - 18:30","18:00 - 19:00","19:00 - 20:00")

    val repo = remember { ReservationsRepository() }
    val scope = rememberCoroutineScope()
    val userId = RekreativaData.currentUser.username

    val peopleRange = remember(terenId) { parsePeopleRange(teren!!.recommendedPeople) ?: (1..1) }
    var selectedPeople by remember(terenId) { mutableStateOf(peopleRange.first) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RekreativaBackground),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                //Header
                item {
                    RekreativaHeader()
                }
                //Slika
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        AsyncImage(
                            model = teren!!.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                    }
                }
                //Info panel
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = teren!!.name.toUpperCase(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF505050)
                        )

                        Spacer(Modifier.height(16.dp))

                        // Sportovi / Cijena / Kapacitet
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InfoBlockSports(sportIcons)
                            InfoIconBlock(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Payments,
                                        contentDescription = null,
                                        tint = RekreativaBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                value = teren!!.pricePerHour,
                                label = "CIJENA"
                            )
                            InfoIconBlock(
                                icon = {
                                    Icon(
                                        imageVector = Icons.Filled.Groups,
                                        contentDescription = null,
                                        tint = RekreativaBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                value = teren!!.recommendedPeople,
                                label = "KAPACITET"
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        Text(
                            text = "FORMULAR ZA REZERVACIJU",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold,
                            color = RekreativaTextGray
                        )

                        Spacer(Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                                .width(240.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(50))
                                .background(RekreativaBlue)
                        )

                        Spacer(Modifier.height(22.dp))

                        //Tip rezervacije
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TIP REZERVACIJE:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827),
                                modifier = Modifier.weight(1f)
                            )

                            RekreativaTogglePill(
                                text = "JEDNOKRATNO",
                                selected = !isWeekly,
                                onClick = { isWeekly = false }
                            )

                            Spacer(Modifier.width(10.dp))

                            RekreativaTogglePill(
                                text = "SEDMIČNO",
                                selected = isWeekly,
                                onClick = { isWeekly = true }
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        //Dan i period
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(Modifier.weight(0.55f)) {
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text = "VRIJEME:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827),
                                )
                            }
                            if (!isWeekly) {
                                Column(Modifier.weight(1f), verticalArrangement = Arrangement.Top) {
                                    DateDropdownField(
                                        value = selectedDate,
                                        onClick = { showDatePicker = true },
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "DATUM",
                                        fontSize = 11.sp,
                                        color = Color(0xFF6B7280),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(Modifier.height(10.dp))
                            } else {
                                Column(modifier = Modifier.weight(1f)) {
                                    DropdownField(
                                        value = selectedDay,
                                        placeholder = "DAN",
                                        options = days,
                                        onSelect = { selectedDay = it }
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = "DAN (svake sedmice)",
                                        fontSize = 11.sp,
                                        color = Color(0xFF6B7280),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            Spacer(Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                DropdownField(
                                    value = selectedPeriod,
                                    placeholder = "VREM. PERIOD",
                                    options = periods,
                                    onSelect = { selectedPeriod = it },
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "VREM. PERIOD",
                                    fontSize = 11.sp,
                                    color = Color(0xFF6B7280),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(Modifier.weight(0.5f)) {
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    text = "TIP DOGAĐAJA:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF111827),
                                )
                                Spacer(Modifier.width(8.dp))
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                DropdownField(
                                    value = eventType,
                                    placeholder = "TIP DOGAĐAJA",
                                    options = events,
                                    onSelect = { eventType = it }
                                )
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "VIDLJIVOST:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF111827),
                            )
                            Spacer(Modifier.width(10.dp))

                            RekreativaTogglePill(
                                text = "JAVNO",
                                modifier = Modifier.width(114.dp),
                                selected = !isPrivate,
                                onClick = { isPrivate = false }
                            )

                            Spacer(Modifier.width(10.dp))

                            RekreativaTogglePill(
                                text = "PRIVATNO",
                                modifier = Modifier.width(114.dp),
                                selected = isPrivate,
                                onClick = { isPrivate = true }
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        PeopleCountPicker(
                            range = peopleRange,
                            value = selectedPeople,
                            onChange = { selectedPeople = it }
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    //Buttoni
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RekreativaReservationButton(
                            text = "REZERVIŠI",
                            isLoading = isSubmitting,
                            enabled = !isSubmitting,
                            onClick = {
                                isSubmitting = true
                                val (from, to) = parsePeriod(selectedPeriod)
                                val type = if (isWeekly) "SEDMICNO" else "JEDNOKRATNO"
                                val dateForDb = if (!isWeekly) selectedDate else ""
                                val dayForDb = if (isWeekly) selectedDay else ""

                                scope.launch {
                                    if (!isWeekly && selectedDate.isBlank()) return@launch

                                    try {
                                        repo.createReservation(
                                            Reservation(
                                                userId = userId,
                                                terenId = teren!!.id,
                                                terenName = teren!!.name,
                                                sportId = teren!!.sportIds.firstOrNull().orEmpty(),
                                                date = dateForDb,
                                                dayOfWeek = dayForDb,
                                                timeFrom = from,
                                                timeTo = to,
                                                tip_dogadjaja = eventType,
                                                tip_rezervacije = type,
                                                status = "PENDING"
                                            )
                                        )
                                        snackbarHostState.showSnackbar("Rezervacija je poslana ✅")
                                    } catch (e: Exception) {
                                        snackbarHostState.showSnackbar("Greška pri rezervaciji")
                                    } finally {
                                        isSubmitting = false
                                    }
                                }
                            },
                            modifier = Modifier.width(140.dp).height(40.dp),
                            textSize = 14.sp
                        )
                    }
                }
            }
        }
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                            selectedDate = formatter.format(Date(millis))
                        }
                        showDatePicker = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Otkaži") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Composable
private fun InfoBlockSports(iconRes: List<Int>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
            iconRes.forEach { res ->
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .shadow(8.dp, CircleShape, clip = false)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text("SPORTOVI", fontSize = 11.sp, color = Color(0xFF6B7280), fontWeight = FontWeight.Bold)
    }
}

@Composable
fun InfoIconBlock(
    icon: @Composable () -> Unit,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(36.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp), clip = false)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                icon()
                Text(
                    text = value,
                    color = RekreativaBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = label,
            fontSize = 11.sp,
            color = RekreativaTextGray,
            fontWeight = FontWeight.Bold
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownField(
    value: String,
    placeholder: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                color = Color(0xFF111827)
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .height(36.dp)
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    Text(
                        text = placeholder,
                        textAlign = TextAlign.Center,
                        color = Color(0xFF9CA3AF),
                        fontSize = 10.sp,
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                interactionSource = remember { MutableInteractionSource() },
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 6.dp,
                    end = 8.dp,
                    bottom = 8.dp
                ),
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = true,
                        isError = false,
                        interactionSource = remember { MutableInteractionSource() },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF0B4CFF),
                            unfocusedBorderColor = Color(0xFFB0B0B0),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                }
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt, fontSize = 12.sp) },
                    onClick = {
                        onSelect(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DateDropdownField(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(18.dp),
            color = Color.White,
            shadowElevation = 0.dp,
            tonalElevation = 0.dp,
            border = BorderStroke(1.dp, RekreativaBorderUnfocused),
            modifier = Modifier.height(36.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = 12.dp, vertical = 0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = if (value.isBlank()) "ODABERI DATUM" else value,
                    fontSize = 10.sp,
                    color = Color(0xFF111827),
                    modifier = Modifier.weight(1f),
                    maxLines = 1
                )

                Icon(
                    imageVector = Icons.Filled.DateRange,
                    modifier = Modifier.size(15.dp),
                    contentDescription = null,
                    tint = Color(0xFF6B7280)
                )
            }
        }
    }
}

private fun parsePeriod(period: String): Pair<String, String> {
    val parts = period.split("-").map { it.trim() }
    val from = parts.getOrNull(0).orEmpty()
    val to = parts.getOrNull(1).orEmpty()
    return from to to
}

private fun parsePeopleRange(input: String): IntRange? {
    val cleaned = input.replace("–", "-").replace("—", "-")
    val parts = cleaned.split("-").map { it.trim() }
    val a = parts.getOrNull(0)?.toIntOrNull()
    val b = parts.getOrNull(1)?.toIntOrNull()
    if (a == null || b == null) return null
    val min = minOf(a, b)
    val max = maxOf(a, b)
    return min..max
}

@Composable
private fun PeopleCountPicker(
    range: IntRange,
    value: Int,
    onChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "BROJ OSOBA:",
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827),
            )
            RekreativaTogglePill(
                text = "−",
                selected = false,
                onClick = {
                    val newVal = (value - 1).coerceAtLeast(range.first)
                    onChange(newVal)
                }
            )

            Text(
                text = "$value",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = RekreativaBlue
            )

            RekreativaTogglePill(
                text = "+",
                selected = false,
                onClick = {
                    val newVal = (value + 1).coerceAtMost(range.last)
                    onChange(newVal)
                }
            )
        }
    }
}
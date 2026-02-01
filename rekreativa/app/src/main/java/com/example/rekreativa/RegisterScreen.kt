package com.example.rekreativa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onGoToLogin:() -> Unit,
) {
    var birthDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    // UI-only state (za TextField)
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9EEF3))
    ) {

        RekreativaHeader()
        Spacer(Modifier.height(14.dp))
        RekreativaHeaderText("REGISTRACIJA")

        Spacer(Modifier.height(5.dp))

        Text(
            text = "Registrujte svoj nalog da nastavite.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF7A7A7A),
            fontSize = 14.sp,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.4f),
                    offset = Offset(0f, 2f),
                    blurRadius = 8f
                )
            )

        )

        Spacer(Modifier.height(30.dp))

        // FORMA
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // koristiš tvoj custom RekreativaTextField (sa ikonama)
            RekreativaTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = "Ime i prezime",
                leadingIcon = Icons.Filled.Person
            )

            RekreativaTextField(
                value = email,
                onValueChange = { email = it },
                label = "E-mail",
                leadingIcon = Icons.Filled.Email
            )

            RekreativaDateField(
                value = birthDate,
                onClick = { showDatePicker = true }
            )


            RekreativaTextField(
                value = password,
                onValueChange = { password = it },
                label = "Lozinka",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true
            )

            RekreativaTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Ponovi lozinku",
                leadingIcon = Icons.Filled.Lock,
                isPassword = true
            )
        }

        Spacer(Modifier.height(30.dp))

        // DUGME
        RekreativaImageButton(
            text = "REGISTRUJ SE",
            onClick = { /* */ },
            modifier = Modifier.padding(horizontal = 90.dp)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Već imaš nalog?",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF7A7A7A),
            fontSize = 12.sp
        )

        RekreativaImageButton(
            text = "PRIJAVI SE",
            onClick =  onGoToLogin,
            modifier = Modifier.padding(horizontal = 90.dp)
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val formatter = java.text.SimpleDateFormat("dd.MM.yyyy.", java.util.Locale.getDefault())
                            birthDate = formatter.format(java.util.Date(millis))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Otkaži")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

}



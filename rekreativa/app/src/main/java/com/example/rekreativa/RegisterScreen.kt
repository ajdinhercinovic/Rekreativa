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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onGoToLogin:() -> Unit,
    onRegisterSuccess: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    fun snack(msg: String) {
        scope.launch { snackbarHostState.showSnackbar(msg) }
    }

    var birthDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    val auth = remember { FirebaseAuth.getInstance() }
    val db = remember { FirebaseFirestore.getInstance() }

    var loading by remember { mutableStateOf(false) }

    fun splitName(fullName: String): Pair<String, String> {
        val parts = fullName.trim().split(Regex("\\s+"))
        if (parts.isEmpty()) return "" to ""
        val ime = parts.first()
        val prezime = if (parts.size >= 2) parts.drop(1).joinToString(" ") else ""
        return ime to prezime
    }

    fun onRegisterClick() {
        val (ime, prezime) = splitName(fullName)
        val e = email.trim()

        if (ime.isBlank() || prezime.isBlank()) { snack("Unesi ime i prezime."); return }
        if (e.isBlank()) { snack("Unesi email."); return }
        if (password.isBlank()) { snack("Unesi lozinku."); return }
        if (repeatPassword.isBlank()) { snack("Ponovi lozinku."); return }
        if (password.length < 6) { snack("Lozinka mora imati najmanje 6 karaktera."); return }
        if (password != repeatPassword) { snack("Lozinke se ne poklapaju."); return }
        if (birthDate.isBlank()) { snack("Odaberi datum rođenja."); return }

        if (loading) return
        loading = true

        auth.createUserWithEmailAndPassword(e, password)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    loading = false
                    val message = when (val ex = task.exception) {
                        is FirebaseAuthException -> ex.toBosnianMessage()
                        else -> "Registracija nije uspjela. Pokušaj ponovo."
                    }
                    snack(message)
                    return@addOnCompleteListener
                }

                val uid = auth.currentUser?.uid
                if (uid == null) {
                    loading = false
                    snack("Greška: UID nije dostupan.")
                    return@addOnCompleteListener
                }

                val username = "${ime}_${prezime}_${uid.take(6)}".lowercase()

                val userDoc = mapOf(
                    "username" to username,
                    "ime" to ime,
                    "prezime" to prezime,
                    "email" to e,
                    "datumRodjenja" to birthDate.trim(),
                    "avatarIndex" to 1,
                    "createdAt" to System.currentTimeMillis()
                )

                db.collection("users").document(uid).set(userDoc)
                    .addOnSuccessListener {
                        loading = false
                        onRegisterSuccess()
                    }
                    .addOnFailureListener {

                        auth.currentUser?.delete()?.addOnCompleteListener {
                            loading = false
                            snack("Greška pri spremanju profila. Pokušaj ponovo.")
                        }
                    }
            }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
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
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    label = "Ponovi lozinku",
                    leadingIcon = Icons.Filled.Lock,
                    isPassword = true
                )
            }

            Spacer(Modifier.height(30.dp))

            RekreativaLoginButton(
                text = "REGISTRUJ SE",
                onClick = { onRegisterClick() },
                isLoading = loading,
                enabled = !loading,
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
                onClick = onGoToLogin,
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
                                val formatter = java.text.SimpleDateFormat(
                                    "dd.MM.yyyy.",
                                    java.util.Locale.getDefault()
                                )
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
}



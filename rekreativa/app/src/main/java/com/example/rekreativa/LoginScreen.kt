package com.example.rekreativa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onGoToRegister:() -> Unit,
    onLoginSucces: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val auth = remember { FirebaseAuth.getInstance() }

    fun onLoginClick() {
        error = null

        val e = email.trim()
        val p = password

        if (e.isBlank()) {
            scope.launch {
                snackbarHostState.showSnackbar("Unesi email.")
            }
            return
        }
        if (p.isBlank()) {
            scope.launch {
                snackbarHostState.showSnackbar("Unesi lozinku.")
            }
            return
        }

        loading = true

        try {
            auth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener { task ->
                    loading = false
                    if (task.isSuccessful) {
                        onLoginSucces()
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                task.exception?.localizedMessage ?: "Neuspješna prijava."
                            )
                        }
                    }
                }
        } catch (ex: Exception) {
            loading = false
            error = ex.localizedMessage ?: "Greška pri prijavi."
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFFFFF))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {

                    RekreativaHeader()
                    Spacer(Modifier.height(14.dp))
                    RekreativaHeaderText("DOBRODOŠLI U REKREATIVU!")

                    Spacer(Modifier.height(5.dp))

                    Text(
                        text = "Potrebno je da se prijavite na Vaš nalog.",
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

                    Spacer(Modifier.height(46.dp))

                    //Inputi
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),

                        ) {
                        RekreativaTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = "E-mail",
                            leadingIcon = Icons.Filled.Email
                        )

                        Spacer(Modifier.height(24.dp))

                        RekreativaTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = "Lozinka",
                            leadingIcon = Icons.Filled.Lock,
                            isPassword = true
                        )
                    }
                    Spacer(Modifier.height(50.dp))

                    RekreativaLoginButton(
                        text = "PRIJAVI SE",
                        isLoading = loading,
                        enabled = !loading,
                        onClick = { onLoginClick() },
                        modifier = Modifier.padding(horizontal = 90.dp)
                    )
                    if (error != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Spacer(Modifier.height(40.dp))

                    Text(
                        text = "Prijavi se uz pomoć:",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF7A7A7A),
                        fontSize = 12.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SocialIconButton(R.drawable.ic_facebook)
                        Spacer(Modifier.width(22.dp))
                        SocialIconButton(R.drawable.ic_instagram)
                        Spacer(Modifier.width(22.dp))
                        SocialIconButton(R.drawable.ic_google)
                    }

                    Spacer(Modifier.height(40.dp))
                    Text(
                        text = "Nemate svoj nalog? Registrujte ga.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color(0xFF7A7A7A),
                        fontSize = 12.sp
                    )

                    Spacer(Modifier.height(10.dp))
                    RekreativaImageButton(
                        text = "REGISTRUJ SE",
                        onClick = onGoToRegister,
                        modifier = Modifier.padding(horizontal = 100.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SocialIconButton(
    iconRes: Int,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(54.dp)
            .shadow(
                elevation = 6.dp,
                shape = CircleShape,
                clip = false
            )
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(26.dp),
            contentScale = ContentScale.Fit
        )
    }
}

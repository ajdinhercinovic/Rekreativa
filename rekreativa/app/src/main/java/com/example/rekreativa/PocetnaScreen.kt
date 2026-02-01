package com.example.rekreativa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.RekreativaData.sports
import com.example.rekreativa.RekreativaData.tereni

@Composable
fun PocetnaScreen(){
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
            RekreativaHeaderText("DOBRODOŠAO ${RekreativaData.currentUser.ime.toUpperCase()}!")

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Nemaš izgovor da ne budeš aktivan.",
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
        }
        item {
            Spacer(Modifier.height(14.dp))
            RekreativaTitle("VAŠ NAREDNI TERMIN")
        }
        val naredniTermin = RekreativaData.termini.firstOrNull()
        if(naredniTermin != null) {
            item {
                TerminCard(naredniTermin) { }
            }
        }
        else{
            item {
                EmptyStateCard(
                    title = "Nema dogovorenih termina",
                    subtitle = "Rezerviši teren ili se pridruži događaju."
                )
                Spacer(Modifier.height(14.dp))
            }
        }

        item{
            RekreativaTitle("PREPORUČUJEMO")
        }
        val preporuceniDogadjaj = RekreativaData.dogadjaji.lastOrNull()
        if(preporuceniDogadjaj != null){
            val teren = tereni.firstOrNull { it.id == preporuceniDogadjaj.lokacijaTerenId }
            val sport = sports.firstOrNull { it.id == preporuceniDogadjaj.sportId }
            item{
                DogadjajCard(
                    dogadjaj = preporuceniDogadjaj,
                    terenNaziv = teren?.naziv ?: "Nepoznata lokacija",
                    sportNaziv = sport?.naziv ?: "Sport",
                    sportIconRes = sport?.iconRes,
                    onAction = { /* */ }
                )
            }
        } else {
            item {
                EmptyStateCard(
                    title = "Trenutno nemamo prijedlog za vas.",
                    subtitle = "Istražite opcije na sekciji Događaji."
                )
                Spacer(Modifier.height(14.dp))
            }
        }
    }
}
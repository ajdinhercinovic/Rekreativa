package com.example.rekreativa

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rekreativa.ui.theme.RekreativaTextGray

@Composable
fun HorizontalSportFilter(
    selectedSports: Set<String>,
    onSelectedSportsChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(Modifier.height(10.dp))

    Text(
        text = "ODABERI SPORT ILI AKTIVNOST KOJU ŽELIŠ IGRATI",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = RekreativaTextGray,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(Modifier.height(8.dp))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RekreativaData.sports.forEach { sport ->
            SportChip(
                sport = sport,
                selected = sport.id in selectedSports,
                onToggle = {
                    val newSet =
                        if (sport.id in selectedSports) selectedSports - sport.id
                        else selectedSports + sport.id
                    onSelectedSportsChange(newSet)
                }
            )
        }
    }
}

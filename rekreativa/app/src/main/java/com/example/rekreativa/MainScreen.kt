package com.example.rekreativa

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.rekreativa.ui.theme.RekreativaBlue

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun MainScreen(
){
    var selectedTab by remember { mutableIntStateOf(0)}
    var openedTerenId by remember { mutableStateOf<String?>(null) }


    val items = listOf(
        BottomNavItem("home", "Početna", Icons.Filled.Home),
        BottomNavItem("tereni", "Tereni", Icons.Filled.Place),
        BottomNavItem("dogadjaji", "Događaji", Icons.Filled.Groups),
        BottomNavItem("termini", "Termini", Icons.Filled.DateRange),
        BottomNavItem("profil", "Profil", Icons.Filled.Person)
    )
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFE9EEF3),
                tonalElevation = 0.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            openedTerenId = null
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = RekreativaBlue,
                            selectedTextColor = RekreativaBlue,
                            unselectedIconColor = Color(0xFF6B7583),   //
                            unselectedTextColor = Color(0xFF6B7583),
                            indicatorColor = Color(0xFFE9EEF3)
                        )
                    )
                }
            }

        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFE9EEF3))
        ) {
            when(selectedTab){
                0 -> PocetnaScreen()
                1 -> {
                    if (openedTerenId == null) {
                        TereniScreen(
                            onOpenTerenDetails = { terenId ->
                                openedTerenId = terenId
                            }
                        )
                    } else {
                        TerenDetailsScreen(
                            terenId = openedTerenId!!,
                            onBack = { openedTerenId = null }
                        )
                    }
                }
                2 -> DogadjajiScreen()
                3 -> TerminiScreen()
                4 -> ProfilScreen()
            }
        }
    }
}
package com.kuralink.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.kuralink.health.ui.screens.BusinessPlanScreen
import com.kuralink.health.ui.screens.HomeScreen
import com.kuralink.health.ui.screens.RehabScreen
import com.kuralink.health.ui.theme.CardBackground
import com.kuralink.health.ui.theme.KuraLinkHealthTheme
import com.kuralink.health.ui.theme.PrimaryDark
import com.kuralink.health.ui.theme.TechBlue
import com.kuralink.health.ui.theme.TextMuted

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Monitorizazioa", Icons.Default.Favorite)
    object Rehab : Screen("rehab", "Errehabilitazioa", Icons.Default.FitnessCenter)
    object Business : Screen("business", "Negozio Plana", Icons.Default.Business)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KuraLinkHealthTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = PrimaryDark,
                    bottomBar = {
                        NavigationBar(
                            containerColor = CardBackground,
                            tonalElevation = 8.dp
                        ) {
                            val items = listOf(Screen.Home, Screen.Rehab, Screen.Business)
                            items.forEach { screen ->
                                val isSelected = currentScreen == screen
                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { currentScreen = screen },
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon,
                                            contentDescription = screen.title
                                        )
                                    },
                                    label = { Text(screen.title) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = TechBlue,
                                        selectedTextColor = TechBlue,
                                        unselectedIconColor = TextMuted,
                                        unselectedTextColor = TextMuted,
                                        indicatorColor = PrimaryDark
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        is Screen.Home -> HomeScreen(modifier = Modifier.padding(innerPadding))
                        is Screen.Rehab -> RehabScreen(modifier = Modifier.padding(innerPadding))
                        is Screen.Business -> BusinessPlanScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

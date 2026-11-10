package com.kuralink.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.ui.screens.CompanyScreen
import com.kuralink.health.ui.screens.HomeScreen
import com.kuralink.health.ui.screens.PatientPortalScreen
import com.kuralink.health.ui.screens.RehabScreen
import com.kuralink.health.ui.theme.*
import com.kuralink.health.util.PdfOpener

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Company : Screen("company", "Enpresa", Icons.Default.Business)
    object Patient : Screen("patient", "Pazientea", Icons.Default.AccountCircle)
    object Monitoring : Screen("monitoring", "Telemetria", Icons.Default.Favorite)
    object Rehab : Screen("rehab", "Ariketak", Icons.Default.FitnessCenter)
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KuraLinkHealthTheme {
                val context = LocalContext.current
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Company) }
                var isLoggedIn by remember { mutableStateOf(false) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = PrimaryDark,
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        color = TechBlue.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(
                                                imageVector = Icons.Default.MonitorHeart,
                                                contentDescription = "Logo",
                                                tint = AccentCyan,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                    Column {
                                        Text(
                                            text = "KuraLink",
                                            color = TextLight,
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "Health Technologies",
                                            color = TextMuted,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            },
                            actions = {
                                // PDF Quick Download Button
                                IconButton(
                                    onClick = { PdfOpener.openOrDownloadPdf(context) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PictureAsPdf,
                                        contentDescription = "Deskargatu PDF Txostena",
                                        tint = AccentCyan
                                    )
                                }

                                // Patient Login Status Indicator
                                Surface(
                                    color = if (isLoggedIn) AccentGreen.copy(alpha = 0.15f) else CardBackground,
                                    shape = RoundedCornerShape(12.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isLoggedIn) AccentGreen else SurfaceBorder),
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(if (isLoggedIn) AccentGreen else TextMuted)
                                        )
                                        Text(
                                            text = if (isLoggedIn) "Mikel I." else "Saioa Hasi",
                                            color = if (isLoggedIn) AccentGreen else TextMuted,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = PrimarySurface,
                                titleContentColor = TextLight
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = PrimarySurface,
                            tonalElevation = 8.dp
                        ) {
                            val items = listOf(Screen.Company, Screen.Patient, Screen.Monitoring, Screen.Rehab)
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
                                    label = { Text(screen.title, fontSize = 11.sp) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = AccentCyan,
                                        selectedTextColor = AccentCyan,
                                        unselectedIconColor = TextMuted,
                                        unselectedTextColor = TextMuted,
                                        indicatorColor = CardBackground
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    when (currentScreen) {
                        is Screen.Company -> CompanyScreen(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToPortal = { currentScreen = Screen.Patient }
                        )
                        is Screen.Patient -> PatientPortalScreen(
                            modifier = Modifier.padding(innerPadding),
                            isLoggedIn = isLoggedIn,
                            onLoginChange = { isLoggedIn = it },
                            onNavigateToMonitoring = { currentScreen = Screen.Monitoring },
                            onNavigateToRehab = { currentScreen = Screen.Rehab }
                        )
                        is Screen.Monitoring -> HomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onNavigateToRehab = { currentScreen = Screen.Rehab }
                        )
                        is Screen.Rehab -> RehabScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

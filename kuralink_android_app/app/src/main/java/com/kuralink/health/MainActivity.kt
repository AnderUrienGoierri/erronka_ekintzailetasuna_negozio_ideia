package com.kuralink.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.ui.screens.CompanyScreen
import com.kuralink.health.ui.screens.HomeScreen
import com.kuralink.health.ui.screens.PatientLoginGate
import com.kuralink.health.ui.screens.PatientPortalScreen
import com.kuralink.health.ui.screens.RehabScreen
import com.kuralink.health.ui.screens.SettingsScreen
import com.kuralink.health.ui.screens.AppLanguage
import com.kuralink.health.ui.theme.*
import com.kuralink.health.util.PdfOpener
import kotlinx.coroutines.launch

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Company : Screen("company", "Enpresa", Icons.Default.Business)
    object Patient : Screen("patient", "Pazientea", Icons.Default.AccountCircle)
    object Monitoring : Screen("monitoring", "Telemetria", Icons.Default.Favorite)
    object Rehab : Screen("rehab", "Ariketak", Icons.Default.FitnessCenter)
    object Settings : Screen("settings", "Ezarpenak", Icons.Default.Settings)
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KuraLinkHealthTheme {
                val context = LocalContext.current
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                var currentScreen by remember { mutableStateOf<Screen>(Screen.Company) }
                var isLoggedIn by remember { mutableStateOf(false) }
                var currentLanguage by remember { mutableStateOf(AppLanguage.EUSKARA) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            drawerContainerColor = PrimarySurface,
                            modifier = Modifier.width(320.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .verticalScroll(rememberScrollState())
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Drawer Header: Corporate Identity
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Surface(
                                            color = CardBackground,
                                            shape = RoundedCornerShape(10.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.5.dp, TechBlue.copy(alpha = 0.6f)),
                                            modifier = Modifier.size(44.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                                contentDescription = "KuraLink Logo",
                                                modifier = Modifier.size(44.dp)
                                            )
                                        }
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text("Kura", color = TextLight, fontSize = 20.sp, fontWeight = FontWeight.Black)
                                                Text("Link", color = AccentCyan, fontSize = 20.sp, fontWeight = FontWeight.Black)
                                            }
                                            Text(
                                                text = "HEALTH TECHNOLOGIES",
                                                color = TextMuted,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 1.sp
                                            )
                                        }
                                    }

                                    IconButton(onClick = { scope.launch { drawerState.close() } }) {
                                        Icon(Icons.Default.Close, contentDescription = "Itxi Menua", tint = TextMuted)
                                    }
                                }

                                // Patient Authentication Status Chip in Drawer
                                Surface(
                                    color = if (isLoggedIn) AccentGreen.copy(alpha = 0.15f) else TechBlue.copy(alpha = 0.12f),
                                    shape = RoundedCornerShape(10.dp),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.dp,
                                        if (isLoggedIn) AccentGreen.copy(alpha = 0.5f) else TechBlue.copy(alpha = 0.3f)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            currentScreen = Screen.Patient
                                            scope.launch { drawerState.close() }
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(10.dp)
                                                .clip(CircleShape)
                                                .background(if (isLoggedIn) AccentGreen else AccentCyan)
                                        )
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = if (isLoggedIn) "Mikel Iturbe (Paziente Aktiboa)" else "Paziente Saioa Hasi GABE",
                                                color = if (isLoggedIn) AccentGreen else TextLight,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = if (isLoggedIn) "Datu klinikoak desblokeatuta" else "Klikatu saioa hasteko",
                                                color = TextMuted,
                                                fontSize = 10.sp
                                            )
                                        }
                                        Icon(
                                            imageVector = if (isLoggedIn) Icons.Default.CheckCircle else Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = if (isLoggedIn) AccentGreen else AccentCyan,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                                // Section 1: Business Plan Sections (teoria_pdf)
                                Text(
                                    text = "Negozio Plana (Txostena)",
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )

                                DrawerNavigationItem(
                                    number = "01",
                                    title = "Arazoa eta Irtenbidea",
                                    subtitle = "Saturazioa eta zaintza biomedikoa",
                                    onClick = {
                                        currentScreen = Screen.Company
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                DrawerNavigationItem(
                                    number = "02",
                                    title = "Misioa, Bisioa eta Baloreak",
                                    subtitle = "Nortasun korporatiboa eta etika",
                                    onClick = {
                                        currentScreen = Screen.Company
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                DrawerNavigationItem(
                                    number = "03",
                                    title = "Target (Xede Publikoa)",
                                    subtitle = "B2B2C segmentazio osoa",
                                    onClick = {
                                        currentScreen = Screen.Company
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                DrawerNavigationItem(
                                    number = "04",
                                    title = "Balio Proposamena & Lehia",
                                    subtitle = "MDR IIa eta Osabide/FHIR",
                                    onClick = {
                                        currentScreen = Screen.Company
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                DrawerNavigationItem(
                                    number = "05",
                                    title = "Marka: KuraLink",
                                    subtitle = "Izenaren garapena eta koloreak",
                                    onClick = {
                                        currentScreen = Screen.Company
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                                // Section 2: Clinical Tools & Data
                                Text(
                                    text = "Tresna Klinikoak & ROI",
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )

                                DrawerToolItem(
                                    icon = Icons.Default.Calculate,
                                    iconColor = AccentCyan,
                                    title = "ROI Kalkulagailua",
                                    subtitle = "Ospitaleen aurrezpen ekonomikoa",
                                    onClick = {
                                        currentScreen = Screen.Company
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                                // Section 3: Patient Secured Medical Area
                                Text(
                                    text = "Pazientearen Eremu Babestua",
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )

                                DrawerToolItem(
                                    icon = Icons.Default.AccountCircle,
                                    iconColor = TechBlue,
                                    title = "Paziente Ataria",
                                    subtitle = if (isLoggedIn) "Mikel Iturberen fitxa klinikoa" else "Hasi saioa sartzeko",
                                    onClick = {
                                        currentScreen = Screen.Patient
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                DrawerToolItem(
                                    icon = Icons.Default.Favorite,
                                    iconColor = AccentCyan,
                                    title = "Telemetria & EKG",
                                    subtitle = if (isLoggedIn) "BioPatch seinale zuzenekoa" else "Babestua (Login beharrezkoa)",
                                    onClick = {
                                        currentScreen = Screen.Monitoring
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                DrawerToolItem(
                                    icon = Icons.Default.FitnessCenter,
                                    iconColor = AccentGreen,
                                    title = "Errehabilitazio Ariketak",
                                    subtitle = if (isLoggedIn) "Agindutako 4 ariketa aktibo" else "Babestua (Login beharrezkoa)",
                                    onClick = {
                                        currentScreen = Screen.Rehab
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                                // Section 4: Configuration & Settings (Ezarpenak)
                                Text(
                                    text = "Ezarpenak & Konfigurazioa",
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )

                                DrawerToolItem(
                                    icon = Icons.Default.Settings,
                                    iconColor = AccentCyan,
                                    title = "Ezarpenak",
                                    subtitle = "Tema (Argia/Iluna), Hizkuntza eta Alertak",
                                    onClick = {
                                        currentScreen = Screen.Settings
                                        scope.launch { drawerState.close() }
                                    }
                                )

                                Spacer(modifier = Modifier.weight(1f))

                                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                                // Bottom Download PDF CTA
                                Button(
                                    onClick = {
                                        PdfOpener.openOrDownloadPdf(context)
                                        scope.launch { drawerState.close() }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.fillMaxWidth().height(46.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PictureAsPdf,
                                        contentDescription = "PDF",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Deskargatu Txostena (PDF)",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                if (isLoggedIn) {
                                    OutlinedButton(
                                        onClick = {
                                            isLoggedIn = false
                                            currentScreen = Screen.Company
                                            scope.launch { drawerState.close() }
                                        },
                                        shape = RoundedCornerShape(10.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, AlertRed),
                                        modifier = Modifier.fillMaxWidth().height(42.dp)
                                    ) {
                                        Icon(Icons.Default.Logout, contentDescription = null, tint = AlertRed, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "Itxi Saioa (Logout)", color = AlertRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = PrimaryDark,
                        topBar = {
                            TopAppBar(
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Ireki Menua",
                                            tint = AccentCyan,
                                            modifier = Modifier.size(26.dp)
                                        )
                                    }
                                },
                                title = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Surface(
                                            color = CardBackground,
                                            shape = RoundedCornerShape(8.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue.copy(alpha = 0.5f)),
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                                contentDescription = "KuraLink Logo",
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "Kura",
                                                color = TextLight,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = "Link",
                                                color = AccentCyan,
                                                fontSize = 17.sp,
                                                fontWeight = FontWeight.ExtraBold,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                },
                                actions = {
                                    // Quick Light/Dark Mode Toggle
                                    IconButton(
                                        onClick = { isDarkThemeState.value = !isDarkThemeState.value }
                                    ) {
                                        Icon(
                                            imageVector = if (isDarkThemeState.value) Icons.Default.LightMode else Icons.Default.DarkMode,
                                            contentDescription = "Aldatu Tema",
                                            tint = AccentCyan
                                        )
                                    }

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
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .clickable {
                                                currentScreen = Screen.Patient
                                            }
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
                            is Screen.Monitoring -> {
                                if (isLoggedIn) {
                                    HomeScreen(
                                        modifier = Modifier.padding(innerPadding),
                                        onNavigateToRehab = { currentScreen = Screen.Rehab }
                                    )
                                } else {
                                    PatientLoginGate(
                                        title = "Telemetria eta EKG Monitorizazioa",
                                        description = "BioPatch sentsore biomedikoaren datu klinikoak eta bihotz-taupaden seinale jarraitua ikusteko, mesedez hasi saioa lehenik.",
                                        onLoginSuccess = { isLoggedIn = true }
                                    )
                                }
                            }
                            is Screen.Rehab -> {
                                if (isLoggedIn) {
                                    RehabScreen(
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                } else {
                                    PatientLoginGate(
                                        title = "Errehabilitazio Plana eta Ariketak",
                                        description = "Medikuak agindutako ariketa fisiologiko pertsonalizatuak eta IMU biomekanika mugimendu analisia ikusteko, mesedez hasi saioa lehenik.",
                                        onLoginSuccess = { isLoggedIn = true }
                                    )
                                }
                            }
                            is Screen.Settings -> {
                                SettingsScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    isDarkTheme = isDarkThemeState.value,
                                    onThemeToggle = { isDarkThemeState.value = it },
                                    currentLanguage = currentLanguage,
                                    onLanguageChange = { currentLanguage = it }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerNavigationItem(
    number: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        color = CardBackground,
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                color = TechBlue.copy(alpha = 0.2f),
                shape = CircleShape,
                modifier = Modifier.size(28.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = number,
                        color = AccentCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column {
                Text(text = title, color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, color = TextMuted, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun DrawerToolItem(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        color = CardBackground,
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                color = iconColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.size(30.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
                }
            }
            Column {
                Text(text = title, color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, color = TextMuted, fontSize = 10.sp)
            }
        }
    }
}

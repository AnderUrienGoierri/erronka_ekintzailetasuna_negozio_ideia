package com.kuralink.health.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.ui.theme.*

enum class AppLanguage(val code: String, val title: String, val subtitle: String) {
    EUSKARA("eu", "Euskara", "Euskadiko osasun-sistema eta interfaze lehenetsia"),
    ESPANOL("es", "Español", "Sistema de salud y terminología clínica en castellano"),
    ENGLISH("en", "English", "International clinical terminology & CE MDR standard")
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    currentLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var arrhythmiaAlerts by remember { mutableStateOf(true) }
    var rehabReminders by remember { mutableStateOf(true) }
    var bleAutoConnect by remember { mutableStateOf(true) }
    var fhirFrequency by remember { mutableStateOf("Denbora errealean") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryDark)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Settings Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                color = TechBlue.copy(alpha = 0.2f),
                shape = CircleShape,
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Ezarpenak",
                        tint = AccentCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column {
                Text(
                    text = "Aplikazioaren Ezarpenak",
                    color = TextLight,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Itxura, hizkuntza eta parametro klinikoak",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Section 1: Appearance & Theme (Modu Argia eta Iluna)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = null,
                            tint = AccentCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Itxura Modua (Tema)",
                                color = TextLight,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isDarkTheme) "Modu Iluna aktibatuta (Gau/Kliniko)" else "Modu Argia aktibatuta (Egun/Kliniko)",
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { onThemeToggle(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AccentCyan,
                            checkedTrackColor = TechBlue,
                            uncheckedThumbColor = TechBlue,
                            uncheckedTrackColor = SurfaceBorder
                        )
                    )
                }

                // Theme Mode Selector Cards
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Dark Mode Card
                    Surface(
                        color = if (isDarkTheme) CardBackground else PrimaryDark,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            if (isDarkTheme) 2.dp else 1.dp,
                            if (isDarkTheme) AccentCyan else SurfaceBorder
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onThemeToggle(true) }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.DarkMode, contentDescription = null, tint = if (isDarkTheme) AccentCyan else TextMuted, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Modu Iluna",
                                color = if (isDarkTheme) AccentCyan else TextLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Light Mode Card
                    Surface(
                        color = if (!isDarkTheme) CardBackground else PrimaryDark,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            if (!isDarkTheme) 2.dp else 1.dp,
                            if (!isDarkTheme) TechBlue else SurfaceBorder
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onThemeToggle(false) }
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.LightMode, contentDescription = null, tint = if (!isDarkTheme) TechBlue else TextMuted, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Modu Argia",
                                color = if (!isDarkTheme) TechBlue else TextLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Language (Hizkuntza Aukera)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Translate, contentDescription = null, tint = TechBlue, modifier = Modifier.size(20.dp))
                    Column {
                        Text(
                            text = "Hizkuntza / Idioma / Language",
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Aukeratu aplikazioaren komunikazio-lengoaia",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // 3 Language Options
                AppLanguage.values().forEach { lang ->
                    val isSelected = currentLanguage == lang
                    Surface(
                        color = if (isSelected) TechBlue.copy(alpha = 0.15f) else CardBackground,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            if (isSelected) 1.5.dp else 1.dp,
                            if (isSelected) TechBlue else SurfaceBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLanguageChange(lang)
                                Toast.makeText(context, "${lang.title} hautatuta", Toast.LENGTH_SHORT).show()
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    color = if (isSelected) TechBlue else SurfaceBorder,
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = lang.code.uppercase(),
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Column {
                                    Text(
                                        text = lang.title,
                                        color = TextLight,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = lang.subtitle,
                                        color = TextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = AccentCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 3: Clinical Alerts & Notifications
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(20.dp))
                    Column {
                        Text(
                            text = "Jakinarazpen Medikoak & Alertak",
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Parametro fisiologikoen kontrol automatikoa",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // Alert Toggle 1: Arritmiak
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Arritmia eta Takikardia Alertak", color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "BPM anormala edo takikardia hautematean soinu-alerta igorri", color = TextMuted, fontSize = 10.sp)
                    }
                    Switch(
                        checked = arrhythmiaAlerts,
                        onCheckedChange = { arrhythmiaAlerts = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = AlertRed, checkedTrackColor = AlertRed.copy(alpha = 0.5f))
                    )
                }

                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                // Alert Toggle 2: Ariketak
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Eguneko Ariketa Oroigarriak", color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "Medikuak agindutako errehabilitazio plana betetzeko abisuak", color = TextMuted, fontSize = 10.sp)
                    }
                    Switch(
                        checked = rehabReminders,
                        onCheckedChange = { rehabReminders = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = AccentCyan, checkedTrackColor = TechBlue)
                    )
                }

                HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                // Alert Toggle 3: BLE Zero-touch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "BioPatch BLE Zero-Touch Lotura", color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "Sentsorea gerturatzean automatikoki sinkronizatu", color = TextMuted, fontSize = 10.sp)
                    }
                    Switch(
                        checked = bleAutoConnect,
                        onCheckedChange = { bleAutoConnect = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = AccentGreen, checkedTrackColor = AccentGreen.copy(alpha = 0.5f))
                    )
                }
            }
        }

        // Section 4: Clinical Interoperability (Osabide FHIR)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.CloudSync, contentDescription = null, tint = TechBlue, modifier = Modifier.size(20.dp))
                    Column {
                        Text(
                            text = "Osabide / HL7 FHIR Sinkronizazioa",
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Osakidetzako zerbitzariekin datu-trukearen maiztasuna",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                val options = listOf("Denbora errealean", "5 minuturo", "15 minuturo")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    options.forEach { opt ->
                        val isSelected = fhirFrequency == opt
                        Surface(
                            color = if (isSelected) TechBlue else CardBackground,
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TechBlue else SurfaceBorder),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { fhirFrequency = opt }
                        ) {
                            Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                Text(
                                    text = opt,
                                    color = if (isSelected) Color.White else TextMuted,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Section 5: Legal & Regulatory Information (CE MDR IIa)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "KuraLink Health Technologies S.L.", color = TextLight, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Surface(
                        color = TechBlue.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "v2.4.0",
                            color = AccentCyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = "Homologazioa: CE Mark Medical Device Class IIa (EB 2017/745 MDR) • ISO 13485:2016",
                    color = TextMuted,
                    fontSize = 10.sp
                )
                Text(
                    text = "Egoitza: Miramongo Parke Zientifiko eta Teknologikoa, Mikeletegi 48, Donostia, Gipuzkoa",
                    color = TextMuted,
                    fontSize = 10.sp
                )
            }
        }
    }
}

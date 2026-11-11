@file:Suppress("unused")

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
import com.kuralink.health.util.AppStrings
import com.kuralink.health.util.PdfOpener
import com.kuralink.health.util.Translations

@Suppress("SpellCheckingInspection")
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
    val strings = remember(currentLanguage) { Translations.forLanguage(currentLanguage) }

    var arrhythmiaAlerts by remember { mutableStateOf(true) }
    var rehabReminders by remember { mutableStateOf(true) }
    var bleAutoConnect by remember { mutableStateOf(true) }
    var fhirFrequency by remember {
        mutableStateOf(
            when (currentLanguage) {
                AppLanguage.EUSKARA -> "Denbora errealean"
                AppLanguage.ESPANOL -> "En tiempo real"
                AppLanguage.ENGLISH -> "Real-time"
            }
        )
    }

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
                        contentDescription = strings.settingsTitle,
                        tint = AccentCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column {
                Text(
                    text = strings.settingsTitle,
                    color = TextLight,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = strings.settingsSubtitle,
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Section 1: Appearance & Theme
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
                                text = strings.themeTitle,
                                color = TextLight,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isDarkTheme) strings.themeDarkDesc else strings.themeLightDesc,
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
                                text = strings.themeDark,
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
                                text = strings.themeLight,
                                color = if (!isDarkTheme) TechBlue else TextLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Language Selector (Hizkuntza Aukera)
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
                            text = strings.langSectionTitle,
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = strings.langSectionSubtitle,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // 3 Language Options (EU, ES, EN)
                AppLanguage.entries.forEach { lang ->
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
                                val msg = when (lang) {
                                    AppLanguage.EUSKARA -> "Euskara hautatuta"
                                    AppLanguage.ESPANOL -> "Español seleccionado"
                                    AppLanguage.ENGLISH -> "English selected"
                                }
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
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

        // Section 3: Negozio Txostena (PDF) Multilingual Options
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
                    Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = AccentCyan, modifier = Modifier.size(20.dp))
                    Column {
                        Text(
                            text = strings.pdfSectionTitle,
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = strings.pdfSectionSubtitle,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // Option 1: Basque PDF
                PdfLanguageOptionItem(
                    badge = "EU",
                    title = strings.pdfOpenEu,
                    subtitle = "Bertsio Ofiziala (9 Orrialde • Donostia)",
                    onClick = { PdfOpener.openOrDownloadPdf(context, AppLanguage.EUSKARA) }
                )

                // Option 2: Spanish PDF
                PdfLanguageOptionItem(
                    badge = "ES",
                    title = strings.pdfOpenEs,
                    subtitle = "Plan de Negocio Completo (9 Páginas)",
                    onClick = { PdfOpener.openOrDownloadPdf(context, AppLanguage.ESPANOL) }
                )

                // Option 3: English PDF
                PdfLanguageOptionItem(
                    badge = "EN",
                    title = strings.pdfOpenEn,
                    subtitle = "Official Business Report (9 Pages)",
                    onClick = { PdfOpener.openOrDownloadPdf(context, AppLanguage.ENGLISH) }
                )
            }
        }

        // Section 4: Clinical Alerts & Notifications
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
                        val title = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Jakinarazpen Medikoak & Alertak"
                            AppLanguage.ESPANOL -> "Notificaciones Médicas y Alertas"
                            AppLanguage.ENGLISH -> "Medical Notifications & Alerts"
                        }
                        val subtitle = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Parametro fisiologikoen kontrol automatikoa"
                            AppLanguage.ESPANOL -> "Control automático de parámetros fisiológicos"
                            AppLanguage.ENGLISH -> "Automatic physiological threshold monitoring"
                        }
                        Text(
                            text = title,
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = subtitle,
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
                        val label = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Arritmia eta Takikardia Alertak"
                            AppLanguage.ESPANOL -> "Alertas de Arritmia y Taquicardia"
                            AppLanguage.ENGLISH -> "Arrhythmia & Tachycardia Alerts"
                        }
                        val desc = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "BPM anormala edo takikardia hautematean soinu-alerta igorri"
                            AppLanguage.ESPANOL -> "Alerta sonora ante detección de BPM anormal o taquicardia"
                            AppLanguage.ENGLISH -> "Sound alert on abnormal BPM or tachycardia detection"
                        }
                        Text(text = label, color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = desc, color = TextMuted, fontSize = 10.sp)
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
                        val label = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Eguneko Ariketa Oroigarriak"
                            AppLanguage.ESPANOL -> "Recordatorios Diarios de Ejercicios"
                            AppLanguage.ENGLISH -> "Daily Exercise Reminders"
                        }
                        val desc = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Medikuak agindutako errehabilitazio plana betetzeko abisuak"
                            AppLanguage.ESPANOL -> "Avisos para completar el plan de rehabilitación pautado"
                            AppLanguage.ENGLISH -> "Reminders to complete prescribed rehabilitation plan"
                        }
                        Text(text = label, color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = desc, color = TextMuted, fontSize = 10.sp)
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
                        val label = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "BioPatch BLE Zero-Touch Lotura"
                            AppLanguage.ESPANOL -> "Vinculación Zero-Touch BLE BioPatch"
                            AppLanguage.ENGLISH -> "BioPatch BLE Zero-Touch Pairing"
                        }
                        val desc = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Sentsorea gerturatzean automatikoki sinkronizatu"
                            AppLanguage.ESPANOL -> "Sincronización automática al aproximar el sensor"
                            AppLanguage.ENGLISH -> "Automatic synchronization on sensor proximity"
                        }
                        Text(text = label, color = TextLight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = desc, color = TextMuted, fontSize = 10.sp)
                    }
                    Switch(
                        checked = bleAutoConnect,
                        onCheckedChange = { bleAutoConnect = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = AccentGreen, checkedTrackColor = AccentGreen.copy(alpha = 0.5f))
                    )
                }
            }
        }

        // Section 5: Clinical Interoperability (Osabide FHIR)
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
                        val title = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Osabide / HL7 FHIR Sinkronizazioa"
                            AppLanguage.ESPANOL -> "Sincronización Osabide / HL7 FHIR"
                            AppLanguage.ENGLISH -> "Osabide / HL7 FHIR Synchronization"
                        }
                        val subtitle = when (currentLanguage) {
                            AppLanguage.EUSKARA -> "Osakidetzako zerbitzariekin datu-trukearen maiztasuna"
                            AppLanguage.ESPANOL -> "Frecuencia de sincronización con Osakidetza"
                            AppLanguage.ENGLISH -> "EHR sync interval with Osakidetza servers"
                        }
                        Text(
                            text = title,
                            color = TextLight,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = subtitle,
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                val options = when (currentLanguage) {
                    AppLanguage.EUSKARA -> listOf("Denbora errealean", "5 minuturo", "15 minuturo")
                    AppLanguage.ESPANOL -> listOf("En tiempo real", "Cada 5 min", "Cada 15 min")
                    AppLanguage.ENGLISH -> listOf("Real-time", "Every 5 min", "Every 15 min")
                }
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

        // Section 6: Legal & Regulatory Information (CE MDR IIa)
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

@Composable
private fun PdfLanguageOptionItem(
    badge: String,
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
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    color = TechBlue.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.size(30.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = badge,
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
            Icon(
                imageVector = Icons.Default.OpenInNew,
                contentDescription = null,
                tint = AccentCyan,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

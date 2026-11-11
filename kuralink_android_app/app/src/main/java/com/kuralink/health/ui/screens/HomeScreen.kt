@file:Suppress("unused")

package com.kuralink.health.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.R
import com.kuralink.health.data.model.SensorDevice
import com.kuralink.health.data.model.VitalSign
import com.kuralink.health.ui.theme.*

@Suppress("SpellCheckingInspection")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToRehab: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var bpmValue by remember { mutableFloatStateOf(72f) }
    var isArrhythmia by remember { mutableStateOf(false) }
    var isFhirSynced by remember { mutableStateOf(true) }

    val currentVitals = remember(bpmValue, isArrhythmia) {
        listOf(
            VitalSign("bpm", "Bihotz Maiztasuna", "${bpmValue.toInt()}", "BPM", if (isArrhythmia) "Arritmia!" else "Egonkorra", !isArrhythmia, "Oraintxe"),
            VitalSign("spo2", "Oxigeno Saturazioa", "98", "% SpO2", "Optimoa", true, "Duela 1 min"),
            VitalSign("bp", "Arterio Presioa", "120 / 78", "mmHg", "Normala", true, "Duela 15 min"),
            VitalSign("recovery", "Errekuperazio Indizea", "88", "%", "Bikaina", true, "Ariketa ondoren")
        )
    }

    val connectedSensors = remember {
        listOf(
            SensorDevice("biopatch", "BioPatch Medical Pro (BLE)", "EKG + PPG", 92, true, "C4:64:E3:88:12:FA"),
            SensorDevice("oximeter", "Oximetro Klinikoa (BLE)", "SpO2", 84, true, "A1:B2:C3:D4:E5:F6")
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
        // Top Bar: Brand + BLE Connection Status
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "KuraLink Telemetria",
                    color = TextLight,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Bihotz-erritmoaren Zuzeneko Jarraipena",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }

            // BLE Status Pill
            Surface(
                color = CardBackground,
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isArrhythmia) AlertRed else AccentCyan)
                    )
                    Text(
                        text = "${connectedSensors.firstOrNull()?.name?.take(8) ?: "BioPatch"} BLE %${connectedSensors.firstOrNull()?.batteryPercent ?: 88}",
                        color = TextLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Live ECG Waveform Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isArrhythmia) AlertRed else SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "EKG Telemetria",
                            tint = if (isArrhythmia) AlertRed else AccentCyan,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "EKG Telemetria Jarraitua",
                            color = TextLight,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = "${bpmValue.toInt()} BPM • ${if (isArrhythmia) "ARRITMIA" else "Egonkorra"}",
                        color = if (isArrhythmia) AlertRed else AccentCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Animated ECG Wave Canvas
                AnimatedEcgCanvas(
                    modifier = Modifier.fillMaxWidth().height(110.dp),
                    bpm = bpmValue,
                    isArrhythmia = isArrhythmia
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Seinale Zarata: < 0.02 mV (MDR Balioztatua)",
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                    Text(
                        text = if (isFhirSynced) "HL7/FHIR Sinkronizatuta" else "Sinkronizazio Zain",
                        color = if (isFhirSynced) AccentGreen else TechBlue,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Interactive Simulation Controls (Web-app-aren antzera)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Simulagailu Klinikoaren Kontrolak",
                    color = TextLight,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                // BPM Slider
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Bihotz-Maiztasuna erregulatu:", color = TextLight, fontSize = 12.sp)
                        Text(text = "${bpmValue.toInt()} BPM", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = bpmValue,
                        onValueChange = { bpmValue = it },
                        valueRange = 45f..150f,
                        steps = 20,
                        colors = SliderDefaults.colors(
                            thumbColor = AccentCyan,
                            activeTrackColor = AccentCyan,
                            inactiveTrackColor = SurfaceBorder
                        )
                    )
                }

                // Arrhythmia Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Arritmia Simulatzailea", color = TextLight, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        Text(text = "Detekzio algoritmikoa probatu", color = TextMuted, fontSize = 11.sp)
                    }
                    Switch(
                        checked = isArrhythmia,
                        onCheckedChange = { isArrhythmia = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = AlertRed,
                            checkedTrackColor = AlertRed.copy(alpha = 0.3f),
                            uncheckedThumbColor = TextMuted,
                            uncheckedTrackColor = SurfaceBorder
                        )
                    )
                }
            }
        }

        // Arrhythmia Alert Banner (if triggered)
        AnimatedVisibility(visible = isArrhythmia) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AlertRed.copy(alpha = 0.15f)),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, AlertRed)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = AlertRed)
                        Text(
                            text = "Arreta: Bihotz-erritmo Anormala Detektatua",
                            color = AlertRed,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "EKG morfologian takikardia bentrikular goiztiarra hauteman da. Datuak Osakidetzako zainketa-zentrora bideratu dira telematikoki.",
                        color = TextLight,
                        fontSize = 11.sp
                    )
                    Button(
                        onClick = {
                            Toast.makeText(context, "Alerta bidalita Dr. Elorzari (Donostia Ospitalea)", Toast.LENGTH_LONG).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Medikuari Premiazko Alerta Igorri", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Vital Signs Grid
        Text(
            text = "Funtsezko Parametro Biomedikoak (${currentVitals.size})",
            color = TextLight,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VitalMetricCard(
                modifier = Modifier.weight(1f),
                title = "Bihotz Maiztasuna",
                value = "${bpmValue.toInt()}",
                unit = "BPM",
                icon = Icons.Default.Favorite,
                accentColor = if (isArrhythmia) AlertRed else TechBlue,
                statusText = if (isArrhythmia) "Arritmia" else "Egonkorra"
            )
            VitalMetricCard(
                modifier = Modifier.weight(1f),
                title = "Oxigeno Saturazioa",
                value = "98",
                unit = "% SpO2",
                icon = Icons.Default.Air,
                accentColor = AccentCyan,
                statusText = "Optimoa"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            VitalMetricCard(
                modifier = Modifier.weight(1f),
                title = "Arterio Presioa",
                value = "120 / 78",
                unit = "mmHg",
                icon = Icons.Default.Speed,
                accentColor = AccentGreen,
                statusText = "Normala"
            )
            VitalMetricCard(
                modifier = Modifier.weight(1f),
                title = "Errekuperazio Indizea",
                value = "88",
                unit = "%",
                icon = Icons.Default.Verified,
                accentColor = AccentCyan,
                statusText = "Bikaina"
            )
        }

        // Primary Call-to-Action Buttons: FHIR Sync & Rehab (Unclipped Layout)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    isFhirSynced = true
                    Toast.makeText(context, "Datuak Osakidetza Osabide FHIR zerbitzarira bidalita!", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                modifier = Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 54.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CloudSync,
                        contentDescription = null,
                        tint = TechBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Osabide",
                            color = TechBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 14.sp
                        )
                        Text(
                            text = "FHIR Datuak",
                            color = TechBlue.copy(alpha = 0.8f),
                            fontSize = 10.sp,
                            lineHeight = 12.sp
                        )
                    }
                }
            }

            Button(
                onClick = onNavigateToRehab,
                colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                modifier = Modifier
                    .weight(1.3f)
                    .defaultMinSize(minHeight = 54.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "Ariketak Hasi",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            lineHeight = 14.sp
                        )
                        Text(
                            text = "Atxikimendua %88",
                            color = AccentCyan,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            lineHeight = 12.sp
                        )
                    }
                }
            }
        }

        // Real Corporate Hardware Photo Card (BioPatch & Smartphone)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.kuralink_app_mockup),
                    contentDescription = "BioPatch sentsore biomedikoa eta KuraLink app",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "BioPatch Gailu Medikoa (CE MDR IIa)",
                            color = TextLight,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Adabaki itsasgarri arina • 7 eguneko bateria",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }
                    Surface(
                        color = TechBlue.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "BLE 5.3",
                            color = TechBlue,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VitalMetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    accentColor: Color,
    statusText: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = value,
                    color = TextLight,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = unit,
                    color = TextMuted,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }

            Surface(
                color = accentColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = statusText,
                    color = accentColor,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
fun AnimatedEcgCanvas(
    modifier: Modifier = Modifier,
    bpm: Float = 72f,
    isArrhythmia: Boolean = false
) {
    val durationMs = ((60f / bpm.coerceAtLeast(30f)) * 1000f).toInt().coerceIn(400, 2500)

    val infiniteTransition = rememberInfiniteTransition(label = "ecgWave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMs, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val midY = height / 2

        // Grid lines
        val gridColor = Color(0xFF1E3A5F).copy(alpha = 0.35f)
        for (i in 0..4) {
            val y = height * (i / 4f)
            drawLine(gridColor, start = androidx.compose.ui.geometry.Offset(0f, y), end = androidx.compose.ui.geometry.Offset(width, y), strokeWidth = 1f)
        }

        // Draw synthetic biomedical ECG rhythm wave
        val path = Path()
        val numCycles = 3
        val cycleWidth = width / numCycles

        for (c in 0 until numCycles) {
            val startX = c * cycleWidth - (phase * cycleWidth)
            val pWaveX = startX + cycleWidth * 0.2f
            val qWaveX = startX + cycleWidth * 0.38f
            val rWaveX = startX + cycleWidth * 0.42f
            val sWaveX = startX + cycleWidth * 0.46f
            val tWaveX = startX + cycleWidth * 0.65f

            if (c == 0) path.moveTo(startX.coerceAtLeast(0f), midY)

            // P wave
            path.lineTo(pWaveX - 10, midY)
            path.quadraticTo(pWaveX, midY - (if (isArrhythmia) 22f else 14f), pWaveX + 10, midY)

            // QRS Complex
            path.lineTo(qWaveX, midY)
            path.lineTo(qWaveX + 4, midY + (if (isArrhythmia) 16f else 8f)) // Q
            path.lineTo(rWaveX, midY - (if (isArrhythmia) 55f else 42f))    // R spike
            path.lineTo(sWaveX, midY + (if (isArrhythmia) 26f else 16f))    // S trough
            path.lineTo(sWaveX + 6, midY)

            // T wave
            path.lineTo(tWaveX - 12, midY)
            path.quadraticTo(tWaveX, midY - (if (isArrhythmia) 8f else 22f), tWaveX + 12, midY)
            path.lineTo(startX + cycleWidth, midY)
        }

        drawPath(
            path = path,
            color = if (isArrhythmia) AlertRed else AccentCyan,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

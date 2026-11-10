package com.kuralink.health.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.ui.theme.*

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

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
                    text = "KuraLink Care",
                    color = TextLight,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Pazientearen Monitorizazio Adimenduna",
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
                            .background(AccentCyan)
                    )
                    Text(
                        text = "BioPatch BLE Aktibo",
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
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
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
                            tint = AccentCyan,
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
                        text = "72 BPM • Egonkorra",
                        color = AccentCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Animated ECG Wave Canvas
                AnimatedEcgCanvas(modifier = Modifier.fillMaxWidth().height(100.dp))

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
                        text = "HL7/FHIR Sinkronizatuta",
                        color = AccentGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Vital Signs Grid
        Text(
            text = "Funtsezko Parametro Biomedikoak",
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
                value = "72",
                unit = "BPM",
                icon = Icons.Default.ShowChart,
                accentColor = TechBlue,
                statusText = "Atseden egoera"
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
                value = "118 / 76",
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

        // Primary Call to Action Button
        Button(
            onClick = { /* Ekintza interaktiboa */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TechBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null
                )
                Text(
                    text = "Gaurko Errehabilitazio Ariketa Hasi",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
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
fun AnimatedEcgCanvas(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "ecgWave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
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
            path.quadraticBezierTo(pWaveX, midY - 14, pWaveX + 10, midY)

            // QRS Complex
            path.lineTo(qWaveX, midY)
            path.lineTo(qWaveX + 4, midY + 8) // Q
            path.lineTo(rWaveX, midY - 42)    // R spike
            path.lineTo(sWaveX, midY + 16)    // S trough
            path.lineTo(sWaveX + 6, midY)

            // T wave
            path.lineTo(tWaveX - 12, midY)
            path.quadraticBezierTo(tWaveX, midY - 22, tWaveX + 12, midY)
            path.lineTo(startX + cycleWidth, midY)
        }

        drawPath(
            path = path,
            color = AccentCyan,
            style = Stroke(width = 3.dp.toPx())
        )
    }
}

@file:Suppress("unused")

package com.kuralink.health.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.ui.theme.*

@Composable
fun RehabScreen(
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
        // Header
        Column {
            Text(
                text = "Errehabilitazio Tele-Zuzendua",
                color = TextLight,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "IMU Sentsore bidezko mugimenduaren biomekanika analisia",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // Compliance Progress Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Aste Honetako Atxikimendua",
                        color = TextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "%88 Beteta",
                        color = TextLight,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Helburua: %80 (Gaindituta)",
                        color = AccentGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Progress Indicator
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(68.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { 0.88f },
                        modifier = Modifier.fillMaxSize(),
                        color = AccentCyan,
                        strokeWidth = 7.dp,
                        trackColor = SurfaceBorder
                    )
                    Text(
                        text = "7/8",
                        color = TextLight,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Active Exercises List
        Text(
            text = "Agindutako Ariketak (Dr. Arantza - Kardiologia)",
            color = TextLight,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        ExerciseItemCard(
            title = "Arnasketa Sakona eta Bular-Hedapena",
            duration = "10 minutu • Egunero",
            status = "Osatuta",
            isCompleted = true
        )

        ExerciseItemCard(
            title = "Mugikortasun Articularra (Belauna eta Orkatila)",
            duration = "15 minutu • 3 serie",
            status = "Egiteke",
            isCompleted = false
        )

        ExerciseItemCard(
            title = "Ibiltze Terapeutikoa Kontrolatua",
            duration = "20 minutu • Pultsu muga: 110 BPM",
            status = "Egiteke",
            isCompleted = false
        )

        // Medical Supervision info banner
        Surface(
            color = TechBlue.copy(alpha = 0.15f),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue.copy(alpha = 0.4f))
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = null,
                    tint = TechBlue
                )
                Text(
                    text = "Ariketa bakoitzeko biomekanika angeluak automatikoki gordetzen dira Osakidetzako txostenean.",
                    color = TextLight,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun ExerciseItemCard(
    title: String,
    duration: String,
    status: String,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isCompleted) AccentGreen.copy(alpha = 0.2f) else TechBlue.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = if (isCompleted) AccentGreen else TechBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = title,
                        color = TextLight,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = duration,
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            }

            Surface(
                color = if (isCompleted) AccentGreen.copy(alpha = 0.15f) else SurfaceBorder,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = status,
                    color = if (isCompleted) AccentGreen else TextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

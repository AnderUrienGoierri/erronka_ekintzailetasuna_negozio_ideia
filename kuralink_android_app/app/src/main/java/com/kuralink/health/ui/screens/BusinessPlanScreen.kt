package com.kuralink.health.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.ui.theme.*

@Composable
fun BusinessPlanScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryDark)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "Negozio Ideiaren Garapena",
                color = TextLight,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "EIP II — 2. Ebaluaketa Erronka | Goierri Eskola",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        // Section 1: Arazoa eta Irtenbidea
        BusinessSectionCard(
            sectionNumber = "01",
            title = "Arazoa eta Irtenbidea",
            icon = Icons.Default.Warning,
            accentColor = TechBlue,
            summary = "Gizartearen zahartze azkarra, gaixo kronikoen gainezkatzea eta etxeko jarraipen kliniko eza.",
            content = listOf(
                "Zer arazo konpontzen dugu?" to "Ospitaleen saturazioa eta pazienteen %50eko ariketa-uztea prebenitzen dugu etxeko zaintza seguruaren bidez.",
                "Nor da bezeroa?" to "B2B eta B2B2C: Osakidetza, ospitale eta klinika pribatuak (Kardiologia, Traumatologia) eta osasun aseguruak.",
                "Zergatik ordainduko luke inork?" to "Ospitaleek ohe-kostuetan milaka euro aurrezten dituztelako eta arrisku kliniko larriak aldez aurretik saihesten dituztelako."
            )
        )

        // Section 2: Misioa, Bisioa eta Baloreak
        BusinessSectionCard(
            sectionNumber = "02",
            title = "Misioa, Bisioa eta Baloreak",
            icon = Icons.Default.Flag,
            accentColor = AccentCyan,
            summary = "Enpresaren nortasuna, epe luzeko helmuga eta lau zutabe etiko nagusiak.",
            content = listOf(
                "Misioa" to "Ingeniaritza biomedikoa eta software multiplataforma uztartzea, pazienteen zaintza ospitaleko hormetatik harago eramateko.",
                "Bisioa (2030)" to "Hego Euskal Herrian eta Europan osasun digitaleko eta tele-monitorizazioko erreferente nagusia bihurtzea.",
                "Baloreak" to "1. Bikaintasun Zientifikoa (MDR), 2. Gizatasuna eta Erabilerraztasuna, 3. Datuen Segurtasuna (GDPR/ENS), 4. Lankidetza Irekia."
            )
        )

        // Section 3: Target (Xede Publikoa)
        BusinessSectionCard(
            sectionNumber = "03",
            title = "Target (Xede Publikoa)",
            icon = Icons.Default.Groups,
            accentColor = AccentGreen,
            summary = "B2B2C segmentazio osoa: erosle instituzionala eta erabiltzaile finala.",
            content = listOf(
                "Demografikoak" to "Erabiltzailea: 50-85 urteko gaixoak. Eroslea: 40-62 urteko Zuzendari Medikoak eta Berrikuntza Buruak.",
                "Geografikoak" to "Euskadi eta Nafarroa (hiriguneak zein landa-eremuak) -> Espainia -> Europa (MDR).",
                "Psikografikoak" to "Pazienteak independentzia eta lasaitasuna bilatzen ditu; medikuak burokrazia gutxitu eta baliozko alertak jaso nahi ditu.",
                "Jokabidezkoak" to "Aldizkako kontrol medikoak eta ariketak etxean modu seguruan osatu nahi dituztenak."
            )
        )

        // Section 4: Balio Proposamena
        BusinessSectionCard(
            sectionNumber = "04",
            title = "Balio Proposamena",
            icon = Icons.Default.Lightbulb,
            accentColor = TechBlue,
            summary = "Gailu anitzeko zero-touch sinkronizazioa eta Osabide/FHIR jatorrizko integrazioa.",
            content = listOf(
                "Balio Bakarra" to "Kontsumo wearableek ez bezala, KuraLink-ek CE MDR ziurtagiri medikoa du eta Osakidetzako historian txertatzen da.",
                "Emaitza Zehatzak" to "Ospitaleratzeak %35 gutxitzea, errehabilitazio atxikimendua %88ra igotzea eta medikuen burokrazia-denbora %40 murriztea.",
                "Abantaila Lehiakorra" to "Makina zahar garestiek ez bezala, mugikorrean dabil (Flutter), merkea da eta zero konfigurazio teknikoa eskatzen du."
            )
        )

        // Section 5: Marka eta Identitatea
        BusinessSectionCard(
            sectionNumber = "05",
            title = "Marka: KuraLink",
            icon = Icons.Default.BrandingWatermark,
            accentColor = AccentCyan,
            summary = "Kura (sendabidea/zaintza) + Link (multiplataforma konexioa).",
            content = listOf(
                "Eslogana" to "Zubi adimenduna gailu medikoen eta zure osasunaren artean.",
                "Irizpideak eta Ezaugarriak" to "Gogoratzeko erraza (3 silaba), ahoskatzeko erraza, jarduerarekin lotua eta bereizgarria.",
                "Kolore Paleta" to "Urdin Sakona (#0A192F), Urdin Teknologikoa (#0284C7), Zian Bitala (#00D4B2)."
            )
        )
    }
}

@Composable
fun BusinessSectionCard(
    sectionNumber: String,
    title: String,
    icon: ImageVector,
    accentColor: Color,
    summary: String,
    content: List<Pair<String, String>>
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = sectionNumber,
                            color = accentColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = title,
                        color = TextLight,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = TextMuted
                )
            }

            Text(
                text = summary,
                color = TextMuted,
                fontSize = 13.sp
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    content.forEach { (subtitle, text) ->
                        Surface(
                            color = PrimarySurface,
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(
                                    text = subtitle,
                                    color = accentColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = text,
                                    color = TextLight,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

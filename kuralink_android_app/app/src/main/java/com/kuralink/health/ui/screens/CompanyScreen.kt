@file:Suppress("unused")

package com.kuralink.health.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.BrandingWatermark
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuralink.health.R
import com.kuralink.health.ui.theme.*
import com.kuralink.health.util.PdfOpener
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CompanyScreen(
    modifier: Modifier = Modifier,
    onNavigateToPortal: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // State for interactive ROI calculator
    var patientCount by remember { mutableFloatStateOf(120f) }
    var savedDays by remember { mutableFloatStateOf(4f) }
    var costPerDay by remember { mutableFloatStateOf(750f) }

    val totalSavedDays = (patientCount * savedDays).toInt()
    val totalSavings = (totalSavedDays * costPerDay).toInt()
    val avoidedReadmissions = (patientCount * 0.35f).toInt()

    val euroFormat = remember {
        NumberFormat.getNumberInstance(Locale.GERMANY)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryDark)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Section: Corporate Brand & Value
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Prominent Corporate Logo Emblem Banner
                Surface(
                    color = CardBackground,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            color = PrimaryDark,
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, AccentCyan),
                            modifier = Modifier.size(52.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = "KuraLink Logo",
                                modifier = Modifier.size(52.dp)
                            )
                        }
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Kura", color = TextLight, fontSize = 22.sp, fontWeight = FontWeight.Black)
                                Text("Link", color = AccentCyan, fontSize = 22.sp, fontWeight = FontWeight.Black)
                            }
                            Text(
                                text = "HEALTH TECHNOLOGIES • CE MDR IIa",
                                color = AccentCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                            Text(
                                text = "Software Biomedikoa & Gailu Adimendunak",
                                color = TextMuted,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Surface(
                    color = CardBackground,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "KuraLink Health Technologies • MedTech SaMD",
                        color = AccentCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Text(
                    text = "Osasun tele-zaintza adimentsua gailu medikoen eta pazienteen artean.",
                    color = TextLight,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp
                )

                Text(
                    text = "KuraLink Health Technologies enpresa korporatiboak paziente kardiologiko eta kronikoen etxeko monitorizazio jarraitua ahalbidetzen du: sentsore biomedikoak zero-touch bidez sinkronizatzen ditu eta Osakidetzako historian zuzenean integratzen da.",
                    color = TextMuted,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                // Key Performance Metrics
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    KpiMetricItem(label = "Ospitaleratzeak", value = "%35", desc = "Murrizketa")
                    KpiMetricItem(label = "Atxikimendua", value = "%88", desc = "Ariketetan")
                    KpiMetricItem(label = "MDR IIa", value = "CE", desc = "Homologatua")
                    KpiMetricItem(label = "Burokrazia", value = "-%40", desc = "Medikuena")
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Action Buttons: PDF Download & Portal Login
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { PdfOpener.openOrDownloadPdf(context) },
                        colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PictureAsPdf,
                            contentDescription = "PDF",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Txostena (PDF)",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = onNavigateToPortal,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentCyan),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Pazientea",
                            tint = AccentCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Paziente Ataria",
                            color = AccentCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Corporate Headquarters Photo Card (Miramon Parkea & I+G Laborategia)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.kuralink_enpresa_irudia),
                    contentDescription = "Miramongo Parke Zientifiko eta Teknologikoa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Miramongo Egoitza & I+G Laborategia",
                            color = TextLight,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            color = TechBlue.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "Donostia",
                                color = TechBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "KuraLink Egoitza Nagusia: I+G Software Garapena eta Ingeniaritza Biomedikoaren laborategi bateratua.",
                        color = TextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Section Title: Business Plan Development (teoria_pdf)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Negozio Ideiaren Garapena",
                    color = TextLight,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Teoria Dokumentuko 5 Atal Nagusiak",
                    color = TextMuted,
                    fontSize = 12.sp
                )
            }
        }

        // Section 1: Arazoa eta Irtenbidea
        ExpandableBusinessCard(
            sectionNumber = "01",
            title = "Arazoa eta Irtenbidea",
            icon = Icons.Default.Warning,
            accentColor = TechBlue,
            summary = "Gizartearen zahartze azkarra, ospitaleen gainezkatzea eta pazienteen ariketa-uztea (%50).",
            details = listOf(
                "Zer arazo konpontzen dugu?" to "Gaixo kroniko eta kardiologikoak euren kabuz geratzen dira ospitaleko alta jaso ondoren. Komplikazio goiztiarrak berandu detektatzen dira, bir-ospitaleratze garestiak sortuz.",
                "Zein da irtenbide biomedikoa?" to "KuraLink tele-zaintza plataforma: pazienteak etxera eramaten duen BioPatch sentsore itsasgarriak EKG jarraitua eta oxigeno-saturazioa neurtzen ditu, datuak Osakidetzako historian (Osabide) zuzenean sartuz.",
                "Nor da bezeroa?" to "B2B/B2B2C: Osakidetza, ospitale eta klinika pribatuak (Quirónsalud, IMQ), eta osasun-aseguruak. Erabiltzaile finala 50-85 urteko gaixoa da.",
                "Zergatik ordainduko luke inork?" to "Ospitale bakoitzak urtean 360.000 € baino gehiago aurrezten dituelako ohe-kostuetan eta larrialdietako gainkargan."
            )
        )

        // Section 2: Misioa, Bisioa eta Baloreak
        ExpandableBusinessCard(
            sectionNumber = "02",
            title = "Misioa, Bisioa eta Baloreak",
            icon = Icons.Default.Flag,
            accentColor = AccentCyan,
            summary = "Ingeniaritza biomedikoa eta software sendoa uztartuz, pazientearen zaintza etxera eramatea.",
            details = listOf(
                "Misioa" to "Software multiplataforma sendoa eta hardware biomediko fidagarria uztartzea, pazienteen zaintza klinikoa ospitaleko hormetatik pazientearen eguneroko bizitzara eramateko segurtasun handienarekin.",
                "Bisioa (2030)" to "Euskal Herrian eta Europako hegoaldean osasun digitaleko eta tele-monitorizazioko erreferente nagusia bihurtzea, Osakidetzako ospitale guztietan eta nazioarteko sare klinikoetan integratuta.",
                "Baloreak" to "1. Bikaintasun Zientifikoa (MDR IIa araudi europarra betetzea).\n2. Gizatasuna eta Irisgarritasuna (adinekoentzat interfaze intuitiboa, zero marruskadura).\n3. Datuen Segurtasuna eta Etika (GDPR eta ENS segurtasun-eskema altua).\n4. Lankidetza Irekia (komunitate medikoarekin eta Osakidetzarekin batera garatua)."
            )
        )

        // Section 3: Target (Xede Publikoa)
        ExpandableBusinessCard(
            sectionNumber = "03",
            title = "Target (Xede Publikoa)",
            icon = Icons.Default.Groups,
            accentColor = AccentGreen,
            summary = "B2B2C segmentazio bikoitza: erosle instituzionala eta erabiltzaile pazientea.",
            details = listOf(
                "Irizpide Demografikoak" to "• Erabiltzailea: 50-85 urteko gizon eta emakumeak, gaixotasun kardiobaskular edo arnas-arazoak dituztenak.\n• Erosle korporatiboa: Ospitaleetako Zuzendari Medikoak eta Berrikuntza Buruak (40-62 urte).",
                "Irizpide Geografikoak" to "Hasieran Euskadi eta Nafarroa (hiriguneak zein landa-eremuetako anbulatorioak). Ondoren Espainiako Estatua eta Europa (CE homologazioari esker).",
                "Irizpide Psikografikoak" to "Pazienteak segurtasuna, lasaitasuna eta independentzia bilatzen ditu. Medikuak burokrazia gutxitu eta informazio fidagarria denbora errealean jaso nahi du.",
                "Irizpide Jokabidezkoak" to "Tratamendua etxean jarraitu nahi duten gaixoak; medikazioa eta errehabilitazio ariketak egitean laguntza behar dutenak."
            )
        )

        // Real Patient at Home Photo Card (Mikel Iturbe)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.kuralink_pazientea_etxean),
                    contentDescription = "Pazientearen Etxeko Tele-Zaintza Zuzenekoa",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pazientearen Etxeko Tele-Zaintza Zuzenekoa",
                            color = TextLight,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            color = AccentGreen.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "B2B2C Eredua",
                                color = AccentGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "Mikel Iturbe (68 urte) bere etxean tele-zaintza medikoarekin, Osakidetzako osasun-profesionalaren jarraipenarekin.",
                        color = TextMuted,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }

        // Section 4: Balio Proposamena eta Konparaketa
        ExpandableBusinessCard(
            sectionNumber = "04",
            title = "Balio Proposamena & Konparaketa",
            icon = Icons.Default.Lightbulb,
            accentColor = TechBlue,
            summary = "CE MDR homologatutako gailu medikoa eta Osabide/FHIR jatorrizko integrazioa.",
            details = listOf(
                "Balio Proposamen Bakarra" to "Kontsumorako erlojuek (Apple Watch, Fitbit) ez bezala, KuraLink-ek CE MDR IIa ziurtagiri klinikoa du, Osakidetzako historian datu mediko ofizial gisa sartzen da eta zero konfigurazio eskatzen dio pazienteari.",
                "KuraLink vs Makina Tradizionalak" to "Holter zaharrak eta ospitaleko dorreak garestiak, deserosoak eta kabledunak dira. KuraLink adabaki arin eta diskretu bat da, mugikorrarekin automatikoki sinkronizatzen dena.",
                "Errehabilitazio Gidatua" to "Monitorizazio pasiboa gainditu eta ariketa gidatu pertsonalizatuak eskaintzen ditu, pazientearen atxikimendua %50etik %88ra igoz."
            )
        )

        // Section 5: Marka eta Identitatea
        ExpandableBusinessCard(
            sectionNumber = "05",
            title = "Marka: KuraLink",
            icon = Icons.AutoMirrored.Filled.BrandingWatermark,
            accentColor = AccentCyan,
            summary = "Kura (sendatzea/zaintza) + Link (multiplataforma konexioa).",
            details = listOf(
                "Izenaren Garapena" to "• 'Kura': Sendatzea, osatzea eta zaintza (euskarazko eta latinezko erroak).\n• 'Link': Teknologia, interoperabilitatea eta gailu medikoen zein mediku-pazienteen arteko lotura.",
                "Eslogan Korporatiboa" to "Zubi adimenduna gailu medikoen eta zure osasunaren artean.",
                "Identitate Bisuala" to "Urdin Sakona (#0A192F, fidagarritasuna), Zian Bitala (#00D4B2, taupada eta bizitza), Urdin Teknologikoa (#0284C7, zehaztasuna).",
                "Irizpideak eta Ezaugarriak" to "Laburra (3 silaba), ahoskatzeko eta gogoratzeko erraza, arlo biomedikoarekin lotura zuzena eta nazioarteko zabalkunderako aproposa."
            )
        )

        // Interactive ROI Calculator Section (Web-app-aren antzekoa)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Calculate,
                        contentDescription = "ROI",
                        tint = AccentCyan,
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = "Ospitale eta Kliniken ROI Kalkulagailua",
                            color = TextLight,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Zenbat diru aurrezten du zentro batek KuraLink-ekin?",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }

                // Slider 1: Paziente kopurua
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Urteko paziente monitorizatuak:", color = TextLight, fontSize = 12.sp)
                        Text(text = "${patientCount.toInt()} gaixo", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = patientCount,
                        onValueChange = { patientCount = it },
                        valueRange = 20f..500f,
                        steps = 23,
                        colors = SliderDefaults.colors(
                            thumbColor = AccentCyan,
                            activeTrackColor = AccentCyan,
                            inactiveTrackColor = SurfaceBorder
                        )
                    )
                }

                // Slider 2: Aurreztutako ohe-egunak
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Aurreztutako ohe-egunak (batez beste):", color = TextLight, fontSize = 12.sp)
                        Text(text = "${savedDays.toInt()} egun", color = TechBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = savedDays,
                        onValueChange = { savedDays = it },
                        valueRange = 1f..10f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = TechBlue,
                            activeTrackColor = TechBlue,
                            inactiveTrackColor = SurfaceBorder
                        )
                    )
                }

                // Calculated Results
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Aurreztutako Egunak", color = TextMuted, fontSize = 10.sp)
                            Text(text = "$totalSavedDays", color = AccentCyan, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Ohe libre ospitalean", color = TextMuted, fontSize = 9.sp)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Guztizko Aurrezpena", color = TextMuted, fontSize = 10.sp)
                            Text(text = "${euroFormat.format(totalSavings)} €", color = AccentGreen, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Urtean aurreztua", color = TextMuted, fontSize = 9.sp)
                        }
                    }
                }

                Surface(
                    color = TechBlue.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Saihestutako Berrospitaleratzeak (%35):", color = TextLight, fontSize = 11.sp)
                        Text(text = "$avoidedReadmissions paziente", color = AccentCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Real Market Data Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, SurfaceBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.TrendingUp, contentDescription = "Merkatu Datuak", tint = AccentCyan, modifier = Modifier.size(20.dp))
                    Text(text = "Lan Mundua eta Demanda Erreala", color = TextLight, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }

                Text(
                    text = "• Euskadiko biztanleriaren %23,5ak 65 urte baino gehiago ditu (Europako batez besteko altuenetakoa).\n• Osasun gastu publikoaren %77 gaixo kronikoak zaintzera bideratzen da.\n• SaMD (Software as a Medical Device) merkatuak urtero %17,8ko hazkunde metatua du (CAGR).\n• Basque Health Cluster eta Osakidetzaren erronka estrategikoa da etxeko tele-zaintza garatzea.",
                    color = TextMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // Bottom CTA: Download Full Business PDF
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PrimarySurface),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, TechBlue.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.DownloadDone,
                    contentDescription = "Deskargatu",
                    tint = AccentCyan,
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "Negozio Txosten Estrategiko Osoa",
                    color = TextLight,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "A4 formatuko 5 orrialdeko txosten korporatiboa, taula konparatiboak, finantza-aurreikuspenak eta Lean Canvas barneratuta.",
                    color = TextMuted,
                    fontSize = 12.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Button(
                    onClick = { PdfOpener.openOrDownloadPdf(context) },
                    colors = ButtonDefaults.buttonColors(containerColor = TechBlue),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.PictureAsPdf, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Deskargatu Txostena (PDF)", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun KpiMetricItem(label: String, value: String, desc: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = AccentCyan, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = label, color = TextLight, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        Text(text = desc, color = TextMuted, fontSize = 10.sp)
    }
}

@Composable
fun ExpandableBusinessCard(
    sectionNumber: String,
    title: String,
    icon: ImageVector,
    accentColor: Color,
    summary: String,
    details: List<Pair<String, String>>
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (expanded) accentColor else SurfaceBorder)
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
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        color = accentColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = sectionNumber,
                                color = accentColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Column {
                        Text(
                            text = title,
                            color = TextLight,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = summary,
                            color = TextMuted,
                            fontSize = 11.sp,
                            maxLines = if (expanded) 10 else 1
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = "Zabaldu",
                    tint = TextMuted
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    HorizontalDivider(color = SurfaceBorder, thickness = 1.dp)

                    details.forEach { (subtitle, text) ->
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = subtitle,
                                color = accentColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = text,
                                color = TextLight,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

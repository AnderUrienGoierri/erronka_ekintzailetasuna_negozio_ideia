@file:Suppress("unused")

package com.kuralink.health.util

import com.kuralink.health.ui.screens.AppLanguage

data class AppStrings(
    val appTitle: String,
    val navCompany: String,
    val navBusinessPlan: String,
    val navPatient: String,
    val navMonitoring: String,
    val navRehab: String,
    val navSettings: String,

    val drawerSectionPlan: String,
    val drawerSectionTools: String,
    val drawerSectionPatient: String,
    val drawerSectionSettings: String,

    val drawerCh1: String,
    val drawerCh2: String,
    val drawerCh3: String,
    val drawerCh4: String,
    val drawerCh5: String,

    val roiCalculatorTitle: String,
    val roiCalculatorSubtitle: String,
    val patientPortalTitle: String,
    val telemetryTitle: String,
    val rehabTitle: String,
    val settingsTitle: String,
    val settingsSubtitle: String,
    val downloadPdfCta: String,
    val patientLoginRequired: String,
    val patientActive: String,

    val themeTitle: String,
    val themeDark: String,
    val themeLight: String,
    val themeDarkDesc: String,
    val themeLightDesc: String,

    val langSectionTitle: String,
    val langSectionSubtitle: String,

    val pdfSectionTitle: String,
    val pdfSectionSubtitle: String,
    val pdfOpenEu: String,
    val pdfOpenEs: String,
    val pdfOpenEn: String,

    val clinicalParamsTitle: String,
    val arrhythmiaAlerts: String,
    val bleAutoConnect: String,
    val fhirSync: String
)

object Translations {
    val EU = AppStrings(
        appTitle = "KuraLink Health",
        navCompany = "Enpresa",
        navBusinessPlan = "Negozio Plana",
        navPatient = "Pazientea",
        navMonitoring = "Telemetria",
        navRehab = "Ariketak",
        navSettings = "Ezarpenak",

        drawerSectionPlan = "Negozio Plana (Txostena)",
        drawerSectionTools = "Tresna Klinikoak & ROI",
        drawerSectionPatient = "Pazientearen Eremu Babestua",
        drawerSectionSettings = "Ezarpenak & Konfigurazioa",

        drawerCh1 = "01. Erronka & Soluzioa",
        drawerCh2 = "02. Misioa & Baloreak",
        drawerCh3 = "03. Target Publikoa",
        drawerCh4 = "04. Balio Proposamena",
        drawerCh5 = "05. Marka: KuraLink",

        roiCalculatorTitle = "ROI Kalkulagailua",
        roiCalculatorSubtitle = "Ospitaleen aurrezpen ekonomikoa",
        patientPortalTitle = "Paziente Ataria",
        telemetryTitle = "Telemetria & EKG",
        rehabTitle = "Errehabilitazio Ariketak",
        settingsTitle = "Ezarpenak",
        settingsSubtitle = "Itxura, hizkuntza eta parametro klinikoak",
        downloadPdfCta = "Deskargatu Txostena (PDF)",
        patientLoginRequired = "Saioa hasi GABE (Klikatu hasteko)",
        patientActive = "Paziente Aktiboa (Datuak desblokeatuta)",

        themeTitle = "Itxura Modua (Tema)",
        themeDark = "Modu Iluna",
        themeLight = "Modu Argia",
        themeDarkDesc = "Modu Iluna aktibatuta (Gau/Kliniko)",
        themeLightDesc = "Modu Argia aktibatuta (Egun/Kliniko)",

        langSectionTitle = "Hizkuntza / Idioma / Language",
        langSectionSubtitle = "Aukeratu aplikazioaren komunikazio-lengoaia",

        pdfSectionTitle = "Negozio Txostena (PDF)",
        pdfSectionSubtitle = "Ireki edo deskargatu txostena hautatutako hizkuntzan",
        pdfOpenEu = "Txostena Euskaraz (Ofiziala)",
        pdfOpenEs = "Plan de Negocio en Español",
        pdfOpenEn = "Business Plan in English",

        clinicalParamsTitle = "Parametro Klinikoak & Sinkronizazioa",
        arrhythmiaAlerts = "Arritmia Alertak",
        bleAutoConnect = "BioPatch BLE Konexio Automatikoa",
        fhirSync = "Osabide / HL7 FHIR Sinkronizazioa"
    )

    val ES = AppStrings(
        appTitle = "KuraLink Health",
        navCompany = "Empresa",
        navBusinessPlan = "Plan de Negocio",
        navPatient = "Paciente",
        navMonitoring = "Telemetría",
        navRehab = "Ejercicios",
        navSettings = "Ajustes",

        drawerSectionPlan = "Plan de Negocio (Informe)",
        drawerSectionTools = "Herramientas Clínicas y ROI",
        drawerSectionPatient = "Área Protegida del Paciente",
        drawerSectionSettings = "Ajustes y Configuración",

        drawerCh1 = "01. Reto y Solución",
        drawerCh2 = "02. Misión y Valores",
        drawerCh3 = "03. Público Objetivo",
        drawerCh4 = "04. Propuesta de Valor",
        drawerCh5 = "05. Marca: KuraLink",

        roiCalculatorTitle = "Calculadora ROI",
        roiCalculatorSubtitle = "Ahorro económico para hospitales",
        patientPortalTitle = "Portal del Paciente",
        telemetryTitle = "Telemetría y ECG",
        rehabTitle = "Ejercicios de Rehabilitación",
        settingsTitle = "Ajustes",
        settingsSubtitle = "Apariencia, idioma y parámetros clínicos",
        downloadPdfCta = "Descargar Informe (PDF)",
        patientLoginRequired = "Sesión NO iniciada (Clic para entrar)",
        patientActive = "Paciente Activo (Datos desbloqueados)",

        themeTitle = "Modo de Apariencia (Tema)",
        themeDark = "Modo Oscuro",
        themeLight = "Modo Claro",
        themeDarkDesc = "Modo Oscuro activado (Noche/Clínico)",
        themeLightDesc = "Modo Claro activado (Día/Clínico)",

        langSectionTitle = "Idioma / Hizkuntza / Language",
        langSectionSubtitle = "Selecciona el idioma de la aplicación",

        pdfSectionTitle = "Plan de Negocio (PDF)",
        pdfSectionSubtitle = "Abre o descarga el informe en el idioma seleccionado",
        pdfOpenEu = "Informe en Euskara (Oficial)",
        pdfOpenEs = "Plan de Negocio en Español",
        pdfOpenEn = "Business Plan en Inglés",

        clinicalParamsTitle = "Parámetros Clínicos y Sincronización",
        arrhythmiaAlerts = "Alertas de Arritmia",
        bleAutoConnect = "Conexión Automática BLE BioPatch",
        fhirSync = "Sincronización Osabide / HL7 FHIR"
    )

    val EN = AppStrings(
        appTitle = "KuraLink Health",
        navCompany = "Company",
        navBusinessPlan = "Business Plan",
        navPatient = "Patient",
        navMonitoring = "Telemetry",
        navRehab = "Exercises",
        navSettings = "Settings",

        drawerSectionPlan = "Business Plan (Report)",
        drawerSectionTools = "Clinical Tools & ROI",
        drawerSectionPatient = "Patient Secured Medical Area",
        drawerSectionSettings = "Settings & Preferences",

        drawerCh1 = "01. Challenge & Solution",
        drawerCh2 = "02. Mission & Values",
        drawerCh3 = "03. Target Audience",
        drawerCh4 = "04. Value Proposition",
        drawerCh5 = "05. Brand: KuraLink",

        roiCalculatorTitle = "ROI Calculator",
        roiCalculatorSubtitle = "Healthcare cost optimization",
        patientPortalTitle = "Patient Portal",
        telemetryTitle = "Telemetry & ECG",
        rehabTitle = "Rehab Exercises",
        settingsTitle = "Settings",
        settingsSubtitle = "Theme, language and clinical parameters",
        downloadPdfCta = "Download Report (PDF)",
        patientLoginRequired = "NOT Logged in (Click to Sign In)",
        patientActive = "Active Patient (Clinical Data Unlocked)",

        themeTitle = "Appearance Mode (Theme)",
        themeDark = "Dark Mode",
        themeLight = "Light Mode",
        themeDarkDesc = "Dark Mode enabled (Night/Clinical)",
        themeLightDesc = "Light Mode enabled (Day/Clinical)",

        langSectionTitle = "Language / Hizkuntza / Idioma",
        langSectionSubtitle = "Select the application interface language",

        pdfSectionTitle = "Business Plan (PDF)",
        pdfSectionSubtitle = "Open or download report in the selected language",
        pdfOpenEu = "Report in Basque (Official)",
        pdfOpenEs = "Business Plan in Spanish",
        pdfOpenEn = "Business Plan in English",

        clinicalParamsTitle = "Clinical Parameters & Synchronization",
        arrhythmiaAlerts = "Arrhythmia Alerts",
        bleAutoConnect = "BioPatch BLE Auto-Connect",
        fhirSync = "Osabide / HL7 FHIR EHR Sync"
    )

    fun forLanguage(lang: AppLanguage): AppStrings = when (lang) {
        AppLanguage.EUSKARA -> EU
        AppLanguage.ESPANOL -> ES
        AppLanguage.ENGLISH -> EN
    }
}

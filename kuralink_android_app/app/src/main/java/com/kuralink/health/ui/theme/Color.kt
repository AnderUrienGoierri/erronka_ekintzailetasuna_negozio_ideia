@file:Suppress("unused")

package com.kuralink.health.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

val isDarkThemeState = mutableStateOf(true)

val PrimaryDark: Color
    get() = if (isDarkThemeState.value) Color(0xFF0A192F) else Color(0xFFF1F5F9)

val PrimarySurface: Color
    get() = if (isDarkThemeState.value) Color(0xFF0F172A) else Color(0xFFFFFFFF)

val CardBackground: Color
    get() = if (isDarkThemeState.value) Color(0xFF1E293B) else Color(0xFFFFFFFF)

val SurfaceBorder: Color
    get() = if (isDarkThemeState.value) Color(0xFF334155) else Color(0xFFCBD5E1)

val TextLight: Color
    get() = if (isDarkThemeState.value) Color(0xFFF8FAFC) else Color(0xFF0F172A)

val TextMuted: Color
    get() = if (isDarkThemeState.value) Color(0xFF94A3B8) else Color(0xFF64748B)

val TechBlue = Color(0xFF0284C7)

val AccentCyan: Color
    get() = if (isDarkThemeState.value) Color(0xFF00D4B2) else Color(0xFF0891B2)

val AccentGreen = Color(0xFF10B981)
val AlertRed = Color(0xFFEF4444)

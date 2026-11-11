@file:Suppress("unused")

package com.kuralink.health.data.model

@Suppress("unused")
data class VitalSign(
    val id: String,
    val title: String,
    val value: String,
    val unit: String,
    val status: String,
    val isNormal: Boolean,
    val timestamp: String
)

@Suppress("unused")
data class SensorDevice(
    val id: String,
    val name: String,
    val type: String,
    val batteryPercent: Int,
    val isConnected: Boolean,
    val macAddress: String
)

@Suppress("unused")
data class BusinessSection(
    val number: String,
    val title: String,
    val summary: String,
    val details: List<Pair<String, String>>
)

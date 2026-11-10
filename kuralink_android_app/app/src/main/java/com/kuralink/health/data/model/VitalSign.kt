package com.kuralink.health.data.model

data class VitalSign(
    val id: String,
    val title: String,
    val value: String,
    val unit: String,
    val status: String,
    val isNormal: Boolean,
    val timestamp: String
)

data class SensorDevice(
    val id: String,
    val name: String,
    val type: String,
    val batteryPercent: Int,
    val isConnected: Boolean,
    val macAddress: String
)

data class BusinessSection(
    val number: String,
    val title: String,
    val summary: String,
    val details: List<Pair<String, String>>
)

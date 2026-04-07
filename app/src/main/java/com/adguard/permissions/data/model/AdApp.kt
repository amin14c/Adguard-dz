package com.adguard.permissions.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ad_apps")
data class AdApp(
    @PrimaryKey val packageName: String,
    val appName: String,
    val riskLevel: RiskLevel,
    val detectedAdLibraries: String,
    val permissions: String
)

enum class RiskLevel {
    HIGH, MEDIUM, LOW
}

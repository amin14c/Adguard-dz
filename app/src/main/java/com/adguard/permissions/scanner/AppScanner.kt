package com.adguard.permissions.scanner

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.adguard.permissions.data.model.AdApp
import com.adguard.permissions.data.model.RiskLevel

class AppScanner(private val context: Context) {
    private val adLibraries = listOf(
        "com.google.android.gms.ads",
        "com.facebook.ads",
        "com.unity3d.ads",
        "com.applovin",
        "com.ironsource",
        "com.chartboost",
        "com.vungle"
    )

    private val riskyPermissions = listOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.READ_CONTACTS",
        "android.permission.READ_PHONE_STATE",
        "android.permission.RECORD_AUDIO",
        "android.permission.CAMERA"
    )

    fun scanApps(): List<AdApp> {
        val pm = context.packageManager
        val packages = pm.getInstalledPackages(PackageManager.GET_PERMISSIONS or PackageManager.GET_META_DATA)
        val adApps = mutableListOf<AdApp>()

        for (pkg in packages) {
            if ((pkg.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0) continue

            val detectedLibs = mutableListOf<String>()
            val detectedPerms = mutableListOf<String>()

            pkg.requestedPermissions?.forEach { perm ->
                if (riskyPermissions.contains(perm)) {
                    detectedPerms.add(perm)
                }
            }

            val riskLevel = when {
                detectedPerms.size >= 3 -> RiskLevel.HIGH
                detectedPerms.isNotEmpty() -> RiskLevel.MEDIUM
                else -> RiskLevel.LOW
            }

            if (detectedPerms.isNotEmpty() || detectedLibs.isNotEmpty()) {
                adApps.add(
                    AdApp(
                        packageName = pkg.packageName,
                        appName = pm.getApplicationLabel(pkg.applicationInfo).toString(),
                        riskLevel = riskLevel,
                        detectedAdLibraries = detectedLibs.joinToString(","),
                        permissions = pkg.requestedPermissions?.joinToString(",") ?: ""
                    )
                )
            }
        }
        return adApps
    }
}

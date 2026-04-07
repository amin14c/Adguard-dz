package com.adguard.permissions.scanner

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class PermissionManager(private val context: Context) {
    fun openAppSettings(packageName: String) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    fun revokePermissionWithRoot(packageName: String, permission: String): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "pm revoke $packageName $permission"))
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
    
    fun hasRootAccess(): Boolean {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
            process.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}

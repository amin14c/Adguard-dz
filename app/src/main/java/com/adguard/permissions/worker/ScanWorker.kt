package com.adguard.permissions.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adguard.permissions.data.AppRepository
import com.adguard.permissions.data.db.AppDatabase
import com.adguard.permissions.scanner.AppScanner

class ScanWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val scanner = AppScanner(applicationContext)
        val apps = scanner.scanApps()
        
        val db = AppDatabase.getDatabase(applicationContext)
        val repo = AppRepository(db.appDao())
        
        repo.refreshApps(apps)
        
        return Result.success()
    }
}

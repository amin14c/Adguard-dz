package com.adguard.permissions.data

import com.adguard.permissions.data.db.AppDao
import com.adguard.permissions.data.model.AdApp
import kotlinx.coroutines.flow.Flow

class AppRepository(private val appDao: AppDao) {
    val allApps: Flow<List<AdApp>> = appDao.getAllApps()

    suspend fun refreshApps(apps: List<AdApp>) {
        appDao.deleteAll()
        appDao.insertAll(apps)
    }

    suspend fun getApp(packageName: String): AdApp? {
        return appDao.getApp(packageName)
    }
}

package com.adguard.permissions.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adguard.permissions.data.model.AdApp
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM ad_apps")
    fun getAllApps(): Flow<List<AdApp>>

    @Query("SELECT * FROM ad_apps WHERE packageName = :packageName")
    suspend fun getApp(packageName: String): AdApp?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apps: List<AdApp>)

    @Query("DELETE FROM ad_apps")
    suspend fun deleteAll()
}

package com.adguard.permissions.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adguard.permissions.data.AppRepository
import com.adguard.permissions.data.db.AppDatabase
import com.adguard.permissions.scanner.AppScanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    private val scanner = AppScanner(application)

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AppRepository(db.appDao())
    }

    val apps = repository.allApps
    
    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning

    fun scanNow() {
        viewModelScope.launch(Dispatchers.IO) {
            _isScanning.value = true
            val scannedApps = scanner.scanApps()
            repository.refreshApps(scannedApps)
            _isScanning.value = false
        }
    }
}

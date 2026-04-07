package com.adguard.permissions.ui.appdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adguard.permissions.data.AppRepository
import com.adguard.permissions.data.db.AppDatabase
import com.adguard.permissions.data.model.AdApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = AppRepository(db.appDao())
    }

    private val _app = MutableStateFlow<AdApp?>(null)
    val app: StateFlow<AdApp?> = _app

    fun loadApp(packageName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _app.value = repository.getApp(packageName)
        }
    }
}

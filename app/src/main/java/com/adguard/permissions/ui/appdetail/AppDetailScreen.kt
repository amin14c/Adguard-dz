package com.adguard.permissions.ui.appdetail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adguard.permissions.scanner.PermissionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailScreen(
    packageName: String,
    onBack: () -> Unit,
    viewModel: AppDetailViewModel = viewModel()
) {
    val context = LocalContext.current
    val permissionManager = remember { PermissionManager(context) }
    val app by viewModel.app.collectAsState()

    LaunchedEffect(packageName) {
        viewModel.loadApp(packageName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(app?.appName ?: "تفاصيل التطبيق") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "رجوع")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        if (app != null) {
            Column(modifier = Modifier.padding(padding).fillMaxSize()) {
                Button(
                    onClick = { permissionManager.openAppSettings(packageName) },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text("فتح إعدادات التطبيق لإلغاء الأذونات")
                }

                Text(
                    text = "الأذونات المطلوبة:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                val perms = app!!.permissions.split(",").filter { it.isNotBlank() }
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(perms) { perm ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(perm.substringAfterLast("."), modifier = Modifier.weight(1f))
                                if (permissionManager.hasRootAccess()) {
                                    Button(onClick = { permissionManager.revokePermissionWithRoot(packageName, perm) }) {
                                        Text("إلغاء (Root)")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

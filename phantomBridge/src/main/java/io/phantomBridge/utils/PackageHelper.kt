package io.phantomBridge.utils

import android.annotation.SuppressLint
import android.content.pm.PackageManager

@SuppressLint("QueryPermissionsNeeded")
internal fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
        packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .any { it.packageName == packageName }
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
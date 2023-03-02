package io.phantomBridge

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import io.phantomBridge.utils.Package.PHANTOM_PACKAGE
import io.phantomBridge.enums.network.NetworkType
import io.phantomBridge.utils.UrlHandler
import io.phantomBridge.utils.isPackageInstalled

class PhantomBridge() {

    private val urlHandler = UrlHandler()

    fun getWallet() = SessionHandler.getWallet()

    fun connectWallet(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        activity: AppCompatActivity,
        appUrl: String,
        packageManager: PackageManager,
        appNotInstalled: () -> Unit
    ) {
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        urlHandler.combineConnectionUrl(
                            redirectScheme,
                            redirectHost,
                            redirectPath,
                            appUrl,
                            NetworkType.MAINNET_BETA
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    fun disconnectWallet(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        activity: AppCompatActivity,
        packageManager: PackageManager,
        appNotInstalled: () -> Unit
    ) {
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        urlHandler.combineDisconnectUrl(
                            redirectScheme, redirectHost, redirectPath
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    fun signUtfMessage(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        activity: AppCompatActivity,
        packageManager: PackageManager,
        message: String,
        appNotInstalled: () -> Unit
    ) {
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        urlHandler.combineSignUtfMessageUrl(
                            redirectScheme, redirectHost, redirectPath, message
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    fun signHexMessage(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        activity: AppCompatActivity,
        packageManager: PackageManager,
        message: String,
        appNotInstalled: () -> Unit
    ) {
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        urlHandler.combineSignHexMessageUrl(
                            redirectScheme, redirectHost, redirectPath, message
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }
}
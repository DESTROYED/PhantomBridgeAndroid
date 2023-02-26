package io.phantomBridge

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import io.phantomBridge.Package.PHANTOM_PACKAGE
import io.phantomBridge.PhantomQuery.DATE_QUERY
import io.phantomBridge.PhantomQuery.NONCE_QUERY
import io.phantomBridge.PhantomQuery.PUBLIC_KEY_QUERY

class PhantomBridge {

    private val urlHandler = UrlHandler()

    fun connectWallet(
        activity: AppCompatActivity,
        redirectScheme: String,
        redirectHost: String,
        appUrl: String,
        packageManager: PackageManager,
        appNotInstalled: () -> Unit
    ) {
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(urlHandler.combineConnectionUrl(redirectScheme, redirectHost, appUrl))
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    fun handleWalletConnection(
        action: String?,
        dataUri: Uri?,
        onWalletConnected: (wallet: String) -> Unit,
        onConnectionError: (error: String) -> Unit
    ) {
        if ((action ?: "") == Intent.ACTION_VIEW) {
            SessionHandler.handleConnection(
                urlHandler.parseQuery(dataUri, PUBLIC_KEY_QUERY),
                urlHandler.parseQuery(dataUri, NONCE_QUERY),
                urlHandler.parseQuery(dataUri, DATE_QUERY),
                onWalletConnected,
                onConnectionError
            )
        }
    }
}
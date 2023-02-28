package io.phantomBridge

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import io.phantomBridge.utils.Package.PHANTOM_PACKAGE
import io.phantomBridge.utils.PhantomQuery.DATE_QUERY
import io.phantomBridge.utils.PhantomQuery.ERROR_CODE
import io.phantomBridge.utils.PhantomQuery.ERROR_MESSAGE
import io.phantomBridge.utils.PhantomQuery.NONCE_QUERY
import io.phantomBridge.utils.PhantomQuery.PUBLIC_KEY_QUERY
import io.phantomBridge.enums.error.ErrorCodes
import io.phantomBridge.enums.error.findErrorCode
import io.phantomBridge.enums.network.NetworkType
import io.phantomBridge.utils.PhantomQuery.SIGN_MESSAGE
import io.phantomBridge.utils.PhantomQuery.WALLET_CONNECTION
import io.phantomBridge.utils.UrlHandler
import io.phantomBridge.utils.isPackageInstalled

class PhantomBridge(private val redirectScheme: String, private val redirectHost: String) {

    private val urlHandler = UrlHandler()

    fun getWallet() = SessionHandler.getWallet()

    fun connectWallet(
        activity: AppCompatActivity,
        appUrl: String,
        packageManager: PackageManager,
        appNotInstalled: () -> Unit
    ) {
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        urlHandler.combineConnectionUrl(
                            redirectScheme,
                            redirectHost,
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
        activity: AppCompatActivity,
        packageManager: PackageManager,
        appNotInstalled: () -> Unit
    ) {
        urlHandler.combineDisconnectUrl(
            redirectScheme,
            redirectHost
        )

        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        urlHandler.combineDisconnectUrl(
                            redirectScheme,
                            redirectHost
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    fun signUtfMessage(
        activity: AppCompatActivity,
        packageManager: PackageManager,
        message: String,
        appNotInstalled: () -> Unit
    ) {
        urlHandler.combineSignUtfMessageUrl(
            redirectScheme,
            redirectHost,
            message
        )
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        urlHandler.combineSignUtfMessageUrl(
                            redirectScheme,
                            redirectHost,
                            message
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    fun signHexMessage(
        activity: AppCompatActivity,
        packageManager: PackageManager,
        message: String,
        appNotInstalled: () -> Unit
    ) {
        urlHandler.combineSignHexMessageUrl(
            redirectScheme,
            redirectHost,
            message
        )
        if (isPackageInstalled(PHANTOM_PACKAGE, packageManager)) {
            activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        urlHandler.combineSignHexMessageUrl(
                            redirectScheme,
                            redirectHost,
                            message
                        )
                    )
                )
            )
        } else {
            appNotInstalled.invoke()
        }
    }

    private fun handleWalletConnection(
        dataUri: Uri,
        onSuccess: (wallet: String) -> Unit,
        onConnectionError: (error: ErrorCodes?) -> Unit
    ) {
        val pubKey = urlHandler.parseQuery(dataUri, PUBLIC_KEY_QUERY)
        val nonce = urlHandler.parseQuery(dataUri, NONCE_QUERY)
        val date = urlHandler.parseQuery(dataUri, DATE_QUERY)
        if (pubKey != null && nonce != null && date != null) {
            SessionHandler.handleConnection(pubKey, nonce, date, onSuccess)
        } else {
            onConnectionError(ErrorCodes.NULL_ARGUMENTS)
        }
    }

    private fun handleSignMessageData(
        dataUri: Uri,
        onSuccess: (wallet: String) -> Unit,
        onConnectionError: (error: ErrorCodes?) -> Unit
    ) {
        val nonce = urlHandler.parseQuery(dataUri, NONCE_QUERY)
        val date = urlHandler.parseQuery(dataUri, DATE_QUERY)
        if (nonce != null && date != null) {
            SessionHandler.handleSignMessageData(nonce, date, onSuccess)
        } else {
            onConnectionError(ErrorCodes.NULL_ARGUMENTS)
        }
    }

    fun handleIntentData(
        action: String?,
        dataUri: Uri,
        onSuccess: (wallet: String) -> Unit,
        onConnectionError: (error: ErrorCodes?) -> Unit
    ) {
        if ((action ?: "") == Intent.ACTION_VIEW) {
            if (dataUri.toString().contains(ERROR_CODE)) {
                urlHandler.parseQuery(dataUri, ERROR_MESSAGE)
                onConnectionError(findErrorCode(urlHandler.parseQuery(dataUri, ERROR_CODE)))
            } else if (dataUri.toString().contains("/$WALLET_CONNECTION/")) {
                handleWalletConnection(dataUri, onSuccess, onConnectionError)
            } else if (dataUri.toString().contains("/$SIGN_MESSAGE/")) {
                handleSignMessageData(dataUri, onSuccess, onConnectionError)
            }
        }
    }
}
package io.phantomBridge

import android.net.Uri
import io.phantomBridge.enums.error.ErrorCodes
import io.phantomBridge.enums.error.findErrorCode
import io.phantomBridge.utils.PhantomQuery
import io.phantomBridge.utils.PhantomQuery.ERROR_CODE
import io.phantomBridge.utils.UrlHandler

class PhantomHandler {

    private val urlHandler = UrlHandler()

    fun handleWalletConnection(
        walletConnectionPath: String,
        action: String,
        dataUri: Uri,
        onSuccess: (wallet: String) -> Unit,
        onConnectionError: (error: ErrorCodes?) -> Unit
    ) {
        checkError(dataUri, walletConnectionPath, action, onConnectionError) {
            val pubKey = urlHandler.parseQuery(dataUri, PhantomQuery.PUBLIC_KEY_QUERY)
            val nonce = urlHandler.parseQuery(dataUri, PhantomQuery.NONCE_QUERY)
            val date = urlHandler.parseQuery(dataUri, PhantomQuery.DATE_QUERY)
            if (pubKey != null && nonce != null && date != null) {
                SessionHandler.handleConnection(pubKey, nonce, date, onSuccess)
            } else {
                onConnectionError(ErrorCodes.NULL_ARGUMENTS)
            }
        }
    }

    fun handleDisconnect(
        walletConnectionPath: String,
        action: String,
        dataUri: Uri,
        onSuccess: () -> Unit,
        onConnectionError: (error: ErrorCodes?) -> Unit
    ) {
        checkError(dataUri, walletConnectionPath, action, onConnectionError) {
            SessionHandler.disconnect(onSuccess)
        }
    }

    fun handleSignMessageData(
        signMessagePath: String,
        action: String,
        dataUri: Uri,
        onSuccess: (wallet: String) -> Unit,
        onConnectionError: (error: ErrorCodes?) -> Unit
    ) {
        checkError(dataUri, signMessagePath, action, onConnectionError) {
            val nonce = urlHandler.parseQuery(dataUri, PhantomQuery.NONCE_QUERY)
            val date = urlHandler.parseQuery(dataUri, PhantomQuery.DATE_QUERY)
            if (nonce != null && date != null) {
                SessionHandler.handleSignMessageData(nonce, date, onSuccess)
            } else {
                onConnectionError(ErrorCodes.NULL_ARGUMENTS)
            }
        }
    }

    private fun checkError(
        dataUri: Uri,
        currentPath: String,
        action: String,
        onConnectionError: (error: ErrorCodes?) -> Unit,
        onHasNoErrors: () -> Unit
    ) {
        if (action == "android.intent.action.VIEW") {
            if (dataUri.toString().contains(ERROR_CODE)) {
                onConnectionError(findErrorCode(urlHandler.parseQuery(dataUri, ERROR_CODE)))
            } else if (!dataUri.toString().contains(currentPath)) {
                onConnectionError(ErrorCodes.WRONG_PATH)
            } else {
                onHasNoErrors()
            }
        } else {
            onConnectionError(ErrorCodes.WRONG_ACTION)
        }
    }

    fun isWalletConnected() = SessionHandler.getWallet().orEmpty().isNotEmpty()
}
package io.phantomBridge

import com.iwebpp.crypto.TweetNacl
import io.phantomBridge.Base58.decode
import io.phantomBridge.Base58.encode
import io.phantomBridge.JsonVariables.PUBLIC_KEY
import io.phantomBridge.JsonVariables.SESSION
import org.json.JSONObject

object SessionHandler {

    private var localPublicKey: String? = null
    private var localPrivateKey: ByteArray? = null
    private var phantomSession: String? = null

    internal fun getPublicKey(): String {
        with(TweetNacl.Box.keyPair()) {
            localPublicKey = encode(publicKey)
            localPrivateKey = secretKey
        }
        return localPublicKey!!
    }

    internal fun handleConnection(
        phantomPublicKey: String?,
        nonce: String?,
        data: String?,
        onWalletConnected: (wallet: String) -> Unit,
        onConnectionError: (error: String) -> Unit
    ) {
        val box = phantomPublicKey?.let {
            TweetNacl.Box(decode(phantomPublicKey), localPrivateKey)
        }

        val dataJson = box?.open(decode(data!!), decode(nonce!!))?.let { String(it) }
            ?.let { JSONObject(it) }


        val wallet = dataJson?.getString(PUBLIC_KEY)
        phantomSession = dataJson?.getString(SESSION)
        wallet?.let { onWalletConnected(it) }
    }

}
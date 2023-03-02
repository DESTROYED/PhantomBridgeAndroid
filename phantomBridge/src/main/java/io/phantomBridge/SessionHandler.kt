package io.phantomBridge

import android.content.SharedPreferences
import android.util.Log
import com.iwebpp.crypto.TweetNacl
import io.phantomBridge.Base58.encode
import io.phantomBridge.utils.JsonVariables.PUBLIC_KEY
import io.phantomBridge.utils.JsonVariables.SESSION
import io.phantomBridge.utils.JsonVariables.SIGNATURE
import io.phantomBridge.utils.SharedPreferencesExtras
import io.phantomBridge.utils.SharedPreferencesExtras.PHANTOM_PUBLIC_KEY
import io.phantomBridge.utils.SharedPreferencesExtras.PRIVATE_KEY
import io.phantomBridge.utils.clear
import io.phantomBridge.utils.getStringIfExists
import io.phantomBridge.utils.putAllStrings
import org.json.JSONObject

object SessionHandler {

    private lateinit var preferences: SharedPreferences

    fun preparePreferences(sharedPreferences: SharedPreferences) {
        preferences = sharedPreferences
    }

    internal fun getWallet() =
        if (preferences.contains(SharedPreferencesExtras.WALLET)) preferences.getString(
            SharedPreferencesExtras.WALLET,
            ""
        ) else null

    internal fun getPublicKey() = if (preferences.contains(SharedPreferencesExtras.PUBLIC_KEY)) {
        preferences.getStringIfExists(SharedPreferencesExtras.PUBLIC_KEY)
    } else {
        with(TweetNacl.Box.keyPair()) {
            preferences.putAllStrings(
                Pair(PUBLIC_KEY, encode(publicKey)), Pair(PRIVATE_KEY, encode(secretKey))
            )
            encode(publicKey)
        }
    }

    internal fun getNonce() = preferences.getStringIfExists(SharedPreferencesExtras.NONCE)

    internal fun getSessionPayload(): String {
        val privateKey = preferences.getStringIfExists(PRIVATE_KEY)
        val phantomPublicKey = preferences.getStringIfExists(PHANTOM_PUBLIC_KEY)
        val nonce = preferences.getStringIfExists(SharedPreferencesExtras.NONCE)
        val session = preferences.getStringIfExists(SharedPreferencesExtras.SESSION)

        return Encryptor(phantomPublicKey, privateKey).encryptPayload(
                createSessionPayload(session),
                nonce
            )
    }

    internal fun getSignHexMessagePayload(message: String): String {
        val privateKey = preferences.getStringIfExists(PRIVATE_KEY)
        val phantomPublicKey = preferences.getStringIfExists(PHANTOM_PUBLIC_KEY)
        val nonce = preferences.getStringIfExists(SharedPreferencesExtras.NONCE)
        val session = preferences.getStringIfExists(SharedPreferencesExtras.SESSION)

        return Encryptor(phantomPublicKey, privateKey).encryptPayload(
                createSignHEXMessagePayload(
                    session,
                    message
                ), nonce
            )
    }

    internal fun getSignUtfMessagePayload(message: String): String {
        val privateKey = preferences.getStringIfExists(PRIVATE_KEY)
        val phantomPublicKey = preferences.getStringIfExists(PHANTOM_PUBLIC_KEY)
        val nonce = preferences.getStringIfExists(SharedPreferencesExtras.NONCE)
        val session = preferences.getStringIfExists(SharedPreferencesExtras.SESSION)

        return Encryptor(phantomPublicKey, privateKey).encryptPayload(
                createSignUTF8MessagePayload(
                    session,
                    message
                ), nonce
            )
    }

    internal fun handleConnection(
        phantomPublicKey: String,
        nonce: String,
        data: String,
        onWalletConnected: (wallet: String) -> Unit
    ) {

        val privateKey = preferences.getStringIfExists(PRIVATE_KEY)

        val connectionData = Encryptor(phantomPublicKey, privateKey).decryptData(data, nonce)
        val jsonData = JSONObject(connectionData)
        val session = jsonData.getString(SESSION)
        val wallet = jsonData.getString(PUBLIC_KEY)

        savePhantomData(nonce, wallet, session, phantomPublicKey)
        onWalletConnected(wallet)
    }

    internal fun disconnect(
        onDisconnected: () -> Unit
    ) {
        preferences.clear()
        onDisconnected()
    }

    internal fun handleSignMessageData(
        nonce: String, data: String, onMessageSigned: (signature: String) -> Unit
    ) {
        val privateKey = preferences.getStringIfExists(PRIVATE_KEY)
        val publicKey = preferences.getStringIfExists(PHANTOM_PUBLIC_KEY)

        val connectionData = Encryptor(publicKey, privateKey).decryptData(data, nonce)
        val jsonData = JSONObject(connectionData)
        val signature = jsonData.getString(SIGNATURE)
        onMessageSigned(signature)
    }

    private fun savePhantomData(
        nonce: String?, wallet: String?, session: String?, phantomPublicKey: String?
    ) {
        preferences.putAllStrings(
            Pair(SharedPreferencesExtras.NONCE, nonce),
            Pair(SharedPreferencesExtras.WALLET, wallet),
            Pair(SharedPreferencesExtras.SESSION, session),
            Pair(SharedPreferencesExtras.PHANTOM_PUBLIC_KEY, phantomPublicKey),
        )
    }

}
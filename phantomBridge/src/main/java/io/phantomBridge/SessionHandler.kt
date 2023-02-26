package io.phantomBridge

import android.content.SharedPreferences
import com.iwebpp.crypto.TweetNacl
import io.phantomBridge.Base58.decode
import io.phantomBridge.Base58.encode
import io.phantomBridge.JsonVariables.PUBLIC_KEY
import io.phantomBridge.JsonVariables.SESSION
import org.json.JSONObject

object SessionHandler {

    private var localPublicKey: String? = null
    private var localPrivateKey: ByteArray? = null
    private var preferences: SharedPreferences? = null

    fun preparePreferences(sharedPreferences: SharedPreferences) {
        preferences = sharedPreferences
    }

    internal fun getWallet() = if (preferences?.contains(SharedPreferencesExtras.WALLET) == true) {
        preferences?.getString(SharedPreferencesExtras.WALLET, "")
    } else {
        null
    }

    internal fun getPublicKey(): String {
        if (preferences?.contains(SharedPreferencesExtras.PUBLIC_KEY) == true) {
            localPublicKey = preferences?.getString(SharedPreferencesExtras.PUBLIC_KEY, "")
            localPrivateKey =
                preferences?.getString(SharedPreferencesExtras.PRIVATE_KEY, "")
                    ?.let { decode(it) }
        } else {
            with(TweetNacl.Box.keyPair()) {
                localPublicKey = encode(publicKey)
                localPrivateKey = secretKey
            }
        }
        return localPublicKey ?: ""
    }

    internal fun handleConnection(
        phantomPublicKey: String?,
        nonce: String?,
        data: String?,
        onWalletConnected: (wallet: String) -> Unit
    ) {
        val box = phantomPublicKey?.let {
            TweetNacl.Box(decode(phantomPublicKey), localPrivateKey)
        }
        data?.let {
            nonce?.let {
                val dataJson = box?.open(decode(data), decode(nonce))
                    ?.let { String(it) }
                    ?.let { JSONObject(it) }
                val wallet = dataJson?.getString(PUBLIC_KEY)
                val session = dataJson?.getString(SESSION)
                savePhantomData(
                    nonce,
                    wallet,
                    session,
                    phantomPublicKey,
                    localPublicKey,
                    localPrivateKey?.let { privateKey -> encode(privateKey) })

                wallet?.let { onWalletConnected(it) }
            }
        }
    }

    private fun savePhantomData(
        nonce: String?,
        wallet: String?,
        session: String?,
        phantomPublicKey: String?,
        localPublicKey: String?,
        localPrivateKey: String?
    ) {
        preferences?.edit()
            ?.putString(SharedPreferencesExtras.NONCE, nonce)
            ?.putString(SharedPreferencesExtras.WALLET, wallet)
            ?.putString(SharedPreferencesExtras.SESSION, session)
            ?.putString(SharedPreferencesExtras.PHANTOM_PUBLIC_KEY, phantomPublicKey)
            ?.putString(SharedPreferencesExtras.PUBLIC_KEY, localPublicKey)
            ?.putString(SharedPreferencesExtras.PRIVATE_KEY, localPrivateKey)
            ?.apply()
    }

}
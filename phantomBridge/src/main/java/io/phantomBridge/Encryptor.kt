package io.phantomBridge

import com.iwebpp.crypto.TweetNacl
import io.phantomBridge.Base58.decode

class Encryptor(private val publicKey: String, private val privateKey: String) {

    fun encryptPayload(payload: String, nonce: String) = Base58.encode(
        TweetNacl.Box(decode(publicKey), decode(privateKey))
            .box(payload.toByteArray(), decode(nonce))
    )

    fun decryptData(data: String, nonce: String) = String(
        TweetNacl.Box(decode(publicKey), decode(privateKey))
            .open(decode(data), decode(nonce))
    )

}

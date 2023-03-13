package io.phantomBridge.utils

internal object PhantomQuery {
    const val ERROR_CODE = "errorCode="
    const val PUBLIC_KEY_QUERY = "phantom_encryption_public_key="
    const val NONCE_QUERY = "nonce="
    const val DATE_QUERY = "data="
    const val DAPP_KEY_QUERY = "dapp_encryption_public_key"
    const val NONCEY_QUERY = "nonce"
    const val PAYLOAD_QUERY = "payload"
    const val CLUSTER = "cluster"
    const val APP_URL_QUERY = "app_url"
    const val REDICRECT_LINK_QUERY = "redirect_link"
}

internal object Package {
    const val PHANTOM_PACKAGE = "app.phantom"
}

internal object Endpoints {
    const val CONNECT_ENDPOINT = "connect"
    const val DISCONNECT_ENDPOINT = "disconnect"
    const val SIGN_MESSAGE_ENDPOINT = "signMessage"
    const val SIGN_TRANSACTION_ENDPOINT = "signTransaction"
}

internal object JsonVariables {
    const val PUBLIC_KEY = "public_key"
    const val SESSION = "session"
    const val SIGNATURE = "signature"
    const val MESSAGE = "message"
    const val TRANSACTION = "transaction"
    const val DISPLAY = "display"
}

internal object SharedPreferencesExtras {
    const val NONCE = "nonce"
    const val WALLET = "wallet"
    const val SESSION = "session"
    const val PHANTOM_PUBLIC_KEY = "phantom_public_key"
    const val PUBLIC_KEY = "public_key"
    const val PRIVATE_KEY = "private_key"
}

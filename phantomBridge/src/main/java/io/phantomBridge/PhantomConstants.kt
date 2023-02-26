package io.phantomBridge

internal object PhantomQuery {
    const val ERROR_CODE = "errorCode="
    const val ERROR_MESSAGE = "errorMessage="
    const val PUBLIC_KEY_QUERY = "phantom_encryption_public_key="
    const val NONCE_QUERY = "nonce="
    const val DATE_QUERY = "data="
    const val DAPP_KEY_QUERY = "dapp_encryption_public_key"
    const val CLUSTER = "cluster"
    const val APP_URL_QUERY = "app_url"
    const val REDICRECT_LINK_QUERY = "redirect_link"
}

internal object Package {
    const val PHANTOM_PACKAGE = "app.phantom"
}

internal object Endpoints {
    const val CONNECT_ENDPOINT = "connect"
}

internal object JsonVariables {
    const val PUBLIC_KEY = "public_key"
    const val SESSION = "session"
}

internal object SharedPreferencesExtras{
    const val NONCE = "nonce"
    const val WALLET = "wallet"
    const val SESSION = "session"
    const val PHANTOM_PUBLIC_KEY = "phantom_public_key"
    const val PUBLIC_KEY = "public_key"
    const val PRIVATE_KEY = "private_key"
}

package io.phantomBridge

import android.net.Uri
import io.phantomBridge.Endpoints.CONNECT_ENDPOINT
import io.phantomBridge.PhantomQuery.APP_URL_QUERY
import io.phantomBridge.PhantomQuery.CLUSTER
import io.phantomBridge.PhantomQuery.DAPP_KEY_QUERY
import io.phantomBridge.PhantomQuery.REDICRECT_LINK_QUERY
import io.phantomBridge.types.NetworkType

internal class UrlHandler {

    fun combineConnectionUrl(
        redirectScheme: String,
        redirectHost: String,
        appUrl: String,
        networkType: NetworkType = NetworkType.MAINNET_BETA
    ) =
        buildEndpoint(CONNECT_ENDPOINT) +
                getQuery(REDICRECT_LINK_QUERY, "$redirectScheme://$redirectHost", true) +
                getQuery(APP_URL_QUERY, appUrl) +
                getQuery(DAPP_KEY_QUERY, SessionHandler.getPublicKey()) +
                getQuery(CLUSTER, networkType.environment)

    fun parseQuery(uri: Uri?, query: String): String {
        uri?.let {
            uri.encodedQuery?.substringAfter(query)?.let {
                return if (it.contains("&")) it.substringBefore("&") else it
            }
            return ""
        }
        return ""
    }


    private fun buildEndpoint(endpoint: String) = "${BuildConfig.PHANTOM_BASE_URL}/$endpoint"

    private fun getQuery(query: String, value: String, isFirstQuery: Boolean = false) =
        "${if (isFirstQuery) "?" else "&"}$query=$value"
}
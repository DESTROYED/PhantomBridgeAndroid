package io.phantomBridge.utils

import android.net.Uri
import io.phantomBridge.BuildConfig
import io.phantomBridge.utils.Endpoints.CONNECT_ENDPOINT
import io.phantomBridge.utils.PhantomQuery.APP_URL_QUERY
import io.phantomBridge.utils.PhantomQuery.CLUSTER
import io.phantomBridge.utils.PhantomQuery.DAPP_KEY_QUERY
import io.phantomBridge.utils.PhantomQuery.REDICRECT_LINK_QUERY
import io.phantomBridge.SessionHandler
import io.phantomBridge.SessionHandler.getNonce
import io.phantomBridge.enums.network.NetworkType
import io.phantomBridge.utils.Endpoints.DISCONNECT_ENDPOINT
import io.phantomBridge.utils.Endpoints.SIGN_MESSAGE_ENDPOINT
import io.phantomBridge.utils.PhantomQuery.NONCEY_QUERY
import io.phantomBridge.utils.PhantomQuery.PAYLOAD_QUERY

internal class UrlHandler {

    fun combineConnectionUrl(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        appUrl: String,
        networkType: NetworkType = NetworkType.MAINNET_BETA
    ) =
        buildEndpoint(CONNECT_ENDPOINT) +
                getQuery(
                    REDICRECT_LINK_QUERY,
                    "$redirectScheme://$redirectHost$redirectPath/",
                    true
                ) +
                getQuery(APP_URL_QUERY, appUrl) +
                getQuery(DAPP_KEY_QUERY, SessionHandler.getPublicKey()) +
                getQuery(CLUSTER, networkType.environment)

    fun combineDisconnectUrl(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String
    ) =
        buildEndpoint(DISCONNECT_ENDPOINT) +
                getQuery(
                    REDICRECT_LINK_QUERY,
                    "$redirectScheme://$redirectHost$redirectPath/",
                    true
                ) +
                getQuery(DAPP_KEY_QUERY, SessionHandler.getPublicKey()) +
                getQuery(NONCEY_QUERY, getNonce()) +
                getQuery(PAYLOAD_QUERY, SessionHandler.getSessionPayload())

    fun combineSignUtfMessageUrl(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        message: String
    ) =
        buildEndpoint(SIGN_MESSAGE_ENDPOINT) +
                getQuery(
                    REDICRECT_LINK_QUERY,
                    "$redirectScheme://$redirectHost$redirectPath/",
                    true
                ) +
                getQuery(DAPP_KEY_QUERY, SessionHandler.getPublicKey()) +
                getQuery(NONCEY_QUERY, getNonce()) +
                getQuery(PAYLOAD_QUERY, SessionHandler.getSignUtfMessagePayload(message))

    fun combineSignHexMessageUrl(
        redirectScheme: String,
        redirectHost: String,
        redirectPath: String,
        message: String
    ) =
        buildEndpoint(SIGN_MESSAGE_ENDPOINT) +
                getQuery(
                    REDICRECT_LINK_QUERY,
                    "$redirectScheme://$redirectHost$redirectPath/",
                    true
                ) +
                getQuery(DAPP_KEY_QUERY, SessionHandler.getPublicKey()) +
                getQuery(NONCEY_QUERY, getNonce()) +
                getQuery(PAYLOAD_QUERY, SessionHandler.getSignHexMessagePayload(message))

    fun parseQuery(uri: Uri?, query: String) =
        uri?.let {
            uri.encodedQuery?.substringAfter(query)?.let {
                if (it.contains("&")) it.substringBefore("&") else it
            }
        }

    private fun buildEndpoint(endpoint: String) = "${BuildConfig.PHANTOM_BASE_URL}/$endpoint"

    private fun getQuery(query: String, value: String, isFirstQuery: Boolean = false) =
        "${if (isFirstQuery) "?" else "&"}$query=$value"
}
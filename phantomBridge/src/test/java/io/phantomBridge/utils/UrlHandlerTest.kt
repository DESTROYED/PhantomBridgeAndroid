package io.phantomBridge.utils

import io.mockk.every
import io.mockk.mockkObject
import io.phantomBridge.SessionHandler
import io.phantomBridge.enums.network.NetworkType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


internal class UrlHandlerTest {

    private val urlHandler = UrlHandler()
    private val redirectScheme = "scheme"
    private val redirectHost = "Host"
    private val redirectPath = "Path"
    private val appUrl = "io.phantomBridge"

    @BeforeEach
    fun mockSessionHandler() {
        mockkObject(SessionHandler)
        every { SessionHandler.getPublicKey() } returns "getPublicKeyTest"
        every { SessionHandler.getNonce() } returns "getNonceTest"
        every { SessionHandler.getSessionPayload() } returns "getSessionPayloadTest"
        every { SessionHandler.getSignUtfMessagePayload(any()) } returns "getSignUtfMessagePayloadTest"
        every { SessionHandler.getSignHexMessagePayload(any()) } returns "getSignHexMessagePayloadTest"
    }

    @Test
    fun combineConnectionUrlNetworkDefault() {
        assertEquals(
            "https://phantom.app/ul/v1/connect?redirect_link=scheme://HostPath/&app_url=io.phantomBridge&dapp_encryption_public_key=getPublicKeyTest&cluster=mainnet-beta",
            urlHandler.combineConnectionUrl(
                redirectScheme, redirectHost, redirectPath, appUrl
            )
        )
    }

    @Test
    fun combineConnectionUrlNetworkMainNetBeta() {
        assertEquals(
            "https://phantom.app/ul/v1/connect?redirect_link=scheme://HostPath/&app_url=io.phantomBridge&dapp_encryption_public_key=getPublicKeyTest&cluster=mainnet-beta",
            urlHandler.combineConnectionUrl(
                redirectScheme, redirectHost, redirectPath, appUrl, NetworkType.MAINNET_BETA
            )
        )
    }

    @Test
    fun combineConnectionUrlNetworkTestNet() {
        assertEquals(
            "https://phantom.app/ul/v1/connect?redirect_link=scheme://Hostio.phantomBridge/&app_url=Path&dapp_encryption_public_key=getPublicKeyTest&cluster=testnet",
            urlHandler.combineConnectionUrl(
                redirectScheme, redirectHost, appUrl, redirectPath, NetworkType.TESTNET
            )
        )
    }

    @Test
    fun combineConnectionUrlNetworkDevNet() {
        assertEquals(
            "https://phantom.app/ul/v1/connect?redirect_link=scheme://Hostio.phantomBridge/&app_url=Path&dapp_encryption_public_key=getPublicKeyTest&cluster=devnet",
            urlHandler.combineConnectionUrl(
                redirectScheme, redirectHost, appUrl, redirectPath, NetworkType.DEVNET
            )
        )
    }

    @Test
    fun combineDisconnectUrl() {
        assertEquals(
            "https://phantom.app/ul/v1/disconnect?redirect_link=scheme://HostPath/&dapp_encryption_public_key=getPublicKeyTest&nonce=getNonceTest&payload=getSessionPayloadTest",
            urlHandler.combineDisconnectUrl(redirectScheme, redirectHost, redirectPath)
        )
    }

    @Test
    fun combineSignUtfMessageUrl() {
        assertEquals(
            "https://phantom.app/ul/v1/signMessage?redirect_link=scheme://HostPath/&dapp_encryption_public_key=getPublicKeyTest&nonce=getNonceTest&payload=getSignUtfMessagePayloadTest",
            urlHandler.combineSignUtfMessageUrl(
                redirectScheme, redirectHost, redirectPath, "TESTMESSAGE"
            )
        )
    }

    @Test
    fun combineSignHexMessageUrl() {
        assertEquals(
            "https://phantom.app/ul/v1/signMessage?redirect_link=scheme://HostPath/&dapp_encryption_public_key=getPublicKeyTest&nonce=getNonceTest&payload=getSignHexMessagePayloadTest",
            urlHandler.combineSignHexMessageUrl(
                redirectScheme, redirectHost, redirectPath, "TESTMESSAGE"
            )
        )
    }

}
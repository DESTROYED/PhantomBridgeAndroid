package io.phantomBridge

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class Base58Test {

    @Test
    fun encodeAndDecode() {
        assertEquals("TEST", Base58.encode(Base58.decode("TEST")))
    }

    @Test
    fun encode() {
        assertEquals("39wFF1", Base58.encode("TEST".toByteArray()))
    }

    @Test
    fun decode() {
        assertArrayEquals("TEST".toByteArray(), Base58.decode("39wFF1"))
    }

    @Test
    fun decodeAndEncode() {
        assertArrayEquals("TEST".toByteArray(), Base58.decode(Base58.encode("TEST".toByteArray())))
    }
}
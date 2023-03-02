package io.phantomBridge

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class EncryptorTest {

    private val encryptor = Encryptor(
        "57qamvaNgtGba8cGaGBqDdoBCHVNiTwPh4H2nCZ7Vrhg",
        "E4rZhLxYQhB3yBLJa1pckSupcuxDzog16TnXt9niMQ24"
    )

    @Test
    fun encryptPayload() {
        assertEquals(
            "3UV9wobYXicYkwTsmduP2dq5akxzrKpVpp6w2Xmo6PdMZQamiqSbM5",
            encryptor.encryptPayload(
                "{ \"session\": \"QWEQWE\" }",
                "PJB1UcxGou3YHmYssBz2CbG1AtwQVcjpg"
            )
        )
    }

    @Test
    fun decryptData() {
        assertEquals(
            "{ \"session\": \"QWEQWE\" }",
            encryptor.decryptData(
                "3UV9wobYXicYkwTsmduP2dq5akxzrKpVpp6w2Xmo6PdMZQamiqSbM5",
                "PJB1UcxGou3YHmYssBz2CbG1AtwQVcjpg"
            )
        )
    }
}
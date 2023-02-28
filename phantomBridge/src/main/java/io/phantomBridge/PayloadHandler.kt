package io.phantomBridge

import io.phantomBridge.utils.JsonVariables
import io.phantomBridge.utils.JsonVariables.SESSION
import org.json.JSONObject

fun createSessionPayload(session: String) = JSONObject().put(SESSION, session).toString()

fun createSignUTF8MessagePayload(session: String, message: String) = JSONObject()
    .put(JsonVariables.DISPLAY, UTF8)
    .put(JsonVariables.MESSAGE, message)
    .put(SESSION, session).toString()

fun createSignHEXMessagePayload(session: String, message: String) = JSONObject()
    .put(JsonVariables.DISPLAY, HEX)
    .put(JsonVariables.MESSAGE, message)
    .put(SESSION, session).toString()

private const val UTF8 = "utf8"
private const val HEX = "hex"
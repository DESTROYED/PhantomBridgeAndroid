package io.phantomBridge

import android.util.Log
import io.phantomBridge.entity.Instruction
import io.phantomBridge.entity.Message
import io.phantomBridge.entity.Transaction
import io.phantomBridge.utils.JsonVariables
import io.phantomBridge.utils.JsonVariables.SESSION
import org.json.JSONArray
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

fun createTransactionPayload(session: String, encodedTransaction: String) = JSONObject()
    .put(JsonVariables.TRANSACTION, encodedTransaction)
    .put(SESSION, session).toString()

fun createTransactionJson(transaction: Transaction) =
    JSONObject().apply {
        put("message", createMessageJson(transaction.message))
        put("signatures", transaction.signature)
    }

private fun createMessageJson(message: Message) = JSONObject().apply {
    put("accountKeys", createAccountKeysJson(message.accountKeys))
    put("instructions", createInstructionsJson(message.instructions))
}

private fun createAccountKeysJson(accountKeys: Array<String>) = JSONArray(accountKeys)
private fun createInstructionsJson(instructions: Array<Instruction>) = JSONArray(instructions.map { instruction ->
    createInstructionJson(instruction)
}.toTypedArray())

private fun createInstructionJson(instruction: Instruction) = JSONObject().apply {
    put("accounts", JSONArray(instruction.accounts))
    put("data", instruction.data)
    put("programIdIndex", instruction.programIdIndex)
    put("recentBlockhash", instruction.recentBlockhash)
}

private const val UTF8 = "utf8"
private const val HEX = "hex"
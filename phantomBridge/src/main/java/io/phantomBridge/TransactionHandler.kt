package io.phantomBridge

import io.phantomBridge.entity.Header
import io.phantomBridge.entity.Instruction
import io.phantomBridge.entity.Message
import io.phantomBridge.entity.Transaction
import org.json.JSONObject

class TransactionHandler {

    fun createTransaction(from: String, to: String) = Transaction(createMessage(from, to))

    fun changeTransactionReceiver(transaction: String, receiver: String) = JSONObject(transaction).apply {
        getJSONObject("message").getJSONArray("accountKeys").put(1, receiver)
    }.toString()

    fun createMessage(from: String, to: String) = Message(
        createAccountKeys(from, to),
        createHeader(),
        arrayOf(createInstructions())
    )

    private fun createAccountKeys(from: String, to: String) = arrayOf(from, to)

    private fun createHeader() = Header(0, 0, 0)

    private fun createInstructions() = Instruction(arrayOf(0), "", 0, "")
}
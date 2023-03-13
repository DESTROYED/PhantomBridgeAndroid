package io.phantomBridge.entity

data class Instruction(val accounts: Array<Int>, val data: String, val programIdIndex: Int, val recentBlockhash: String)
package io.phantomBridge.entity

data class Message(val accountKeys: Array<String>, val header: Header, val instructions: Array<Instruction>)

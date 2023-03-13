package io.phantomBridge.entity

data class Header(
    val numReadonlySignedAccounts: Int,
    val numReadonlyUnsignedAccounts: Int,
    val numRequiredSignatures: Int
)
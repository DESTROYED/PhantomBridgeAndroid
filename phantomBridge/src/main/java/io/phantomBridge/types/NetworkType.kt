package io.phantomBridge.types

enum class NetworkType : Network {
    MAINNET_BETA {
        override val environment = "mainnet-beta"
    },
    TESTNET {
        override val environment = "testnet"
    },
    DEVNET {
        override val environment = "devnet"
    }
}
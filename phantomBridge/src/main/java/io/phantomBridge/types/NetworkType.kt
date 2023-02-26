package io.phantomBridge.types

import io.phantomBridge.Network

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
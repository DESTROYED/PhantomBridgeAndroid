package io.phantomBridge

enum class ErrorCodes : Error {
    DISCONNECTED {
        override val errorCode = "4900"
    },
    UNAUTHORIZED {
        override val errorCode = "4100"
    },
    USER_REJECTED_REQUEST {
        override val errorCode = "4001"
    },
    INVALID_INPUT {
        override val errorCode = "-32000"
    },
    REQUESTED_SOURCE_NOT_AVAILABLE {
        override val errorCode = "-32002"
    },
    TRANSACTION_REJECTED {
        override val errorCode = "-32003"
    },
    METHOD_NOT_FOUND {
        override val errorCode = "-32601"
    },
    INTERNAL_ERROR {
        override val errorCode = "-32603"
    }
}

fun findErrorCode(code: String) = ErrorCodes.values().find { it.errorCode == code }

package io.phantomBridge.enums.error

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
    },
    NULL_ARGUMENTS {
        override val errorCode = "0"
    },
    WRONG_PATH {
        override val errorCode = "1"
    },
    WRONG_ACTION {
        override val errorCode = "2"
    }
}

fun findErrorCode(code: String?) = ErrorCodes.values().find { it.errorCode == code }

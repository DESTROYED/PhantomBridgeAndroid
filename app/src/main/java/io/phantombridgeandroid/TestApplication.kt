package io.phantombridgeandroid

import android.app.Application
import android.content.ContextWrapper
import io.phantomBridge.SessionHandler

internal class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SessionHandler.preparePreferences(
            getSharedPreferences(packageName + "_preferences", ContextWrapper.MODE_PRIVATE)
        )
    }
}
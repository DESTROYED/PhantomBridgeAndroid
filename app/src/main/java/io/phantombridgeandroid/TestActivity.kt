package io.phantombridgeandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.phantomBridge.PhantomBridge
import io.phantomBridgeandroid.R

class TestActivity : AppCompatActivity() {

    private val phantomBridge = PhantomBridge()
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_connnect_wallet)
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.connectButton).setOnClickListener {
            phantomBridge.connectWallet(
                this,
                getString(R.string.deep_link_scheme),
                getString(R.string.deep_link_host),
                getString(R.string.appUrl),
                packageManager
            ) {
                Toast.makeText(this, "Phantom app is not installed ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        phantomBridge.handleWalletConnection(intent.action, intent.data, {
            Toast.makeText(this, "My wallet is " + it, Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "Looks like we got an error: " + it, Toast.LENGTH_SHORT).show()
        })
    }
}
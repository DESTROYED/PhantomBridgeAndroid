package io.phantombridgeandroid

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.phantomBridge.PhantomBridge
import io.phantomBridgeandroid.R

class TestActivity : AppCompatActivity() {

    private lateinit var phantomBridge: PhantomBridge
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_connnect_wallet)
        super.onCreate(savedInstanceState)

        val messageEditText = findViewById<EditText>(R.id.messageEditText)
        val disconnectButton = findViewById<View>(R.id.disconnectButton)
        val signUtfMessageButton = findViewById<View>(R.id.signUtfMessageButton)
        val signHexMessageButton = findViewById<View>(R.id.signHexMessageButton)
        val walletTextView = findViewById<TextView>(R.id.walletTextView)
        val connectButton = findViewById<View>(R.id.connectButton)

        phantomBridge =
            PhantomBridge(getString(R.string.deep_link_scheme), getString(R.string.deep_link_host))
        if (!phantomBridge.getWallet().isNullOrEmpty()) {
            connectButton.visibility = View.GONE
            disconnectButton.visibility = View.VISIBLE
            signUtfMessageButton.visibility = View.VISIBLE
            signHexMessageButton.visibility = View.VISIBLE
            messageEditText.visibility = View.VISIBLE
            walletTextView.visibility = View.VISIBLE
            walletTextView.text = phantomBridge.getWallet()
        }

        connectButton.setOnClickListener {
            if (phantomBridge.getWallet().isNullOrEmpty()) {
                phantomBridge.connectWallet(
                    this,
                    getString(R.string.appUrl),
                    packageManager
                ) {
                    Toast.makeText(this, "Phantom app is not installed ", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, "App already connected ", Toast.LENGTH_SHORT).show()
        }

        disconnectButton.setOnClickListener {
            phantomBridge.disconnectWallet(this, packageManager) {

            }
        }

        signUtfMessageButton.setOnClickListener {
            phantomBridge.signUtfMessage(this, packageManager, messageEditText.text.toString()) {

            }
        }

        signHexMessageButton.setOnClickListener {
            phantomBridge.signHexMessage(this, packageManager, messageEditText.text.toString()) {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        intent.data?.let {
            phantomBridge.handleIntentData(intent.action, it, {
                Toast.makeText(this, "Your action succeed " + it, Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(this, "Looks like we got an error: " + it, Toast.LENGTH_SHORT).show()
            })
        }
    }
}
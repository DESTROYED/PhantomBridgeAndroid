package io.phantombridgeandroid

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.phantomBridge.PhantomBridge
import io.phantomBridge.PhantomHandler
import io.phantomBridgeandroid.R
import io.phantomBridgeandroid.R.id.sendTransactionButton

internal class TestActivity : AppCompatActivity() {

    private lateinit var phantomBridge: PhantomBridge
    private lateinit var phantomHandler: PhantomHandler
    private lateinit var messageEditText: EditText
    private lateinit var disconnectButton: View
    private lateinit var signUtfMessageButton: View
    private lateinit var signHexMessageButton: View
    private lateinit var walletTextView: TextView
    private lateinit var connectButton: View
    private lateinit var sendTransactionButton: View

    private lateinit var connectionWalletPath: String
    private lateinit var messageSigningPath: String
    private lateinit var disconnectionPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_connnect_wallet)
        super.onCreate(savedInstanceState)

        sendTransactionButton = findViewById<View>(R.id.sendTransactionButton)
        messageEditText = findViewById<EditText>(R.id.messageEditText)
        disconnectButton = findViewById<View>(R.id.disconnectButton)
        signUtfMessageButton = findViewById<View>(R.id.signUtfMessageButton)
        signHexMessageButton = findViewById<View>(R.id.signHexMessageButton)
        walletTextView = findViewById<TextView>(R.id.walletTextView)
        connectButton = findViewById<View>(R.id.connectButton)
        connectionWalletPath = getString(R.string.path_connection)
        messageSigningPath = getString(R.string.path_sign_message)
        disconnectionPath = getString(R.string.path_disconnection)

        val redirectScheme = getString(R.string.deep_link_scheme)
        val redirectHost = getString(R.string.deep_link_host)

        phantomBridge = PhantomBridge()
        phantomHandler = PhantomHandler()
        if (!phantomBridge.getWallet().isNullOrEmpty()) {
            connectButton.visibility = View.GONE
            disconnectButton.visibility = View.VISIBLE
            signUtfMessageButton.visibility = View.VISIBLE
            signHexMessageButton.visibility = View.VISIBLE
            messageEditText.visibility = View.VISIBLE
            walletTextView.visibility = View.VISIBLE
            sendTransactionButton.visibility = View.VISIBLE
            walletTextView.text = phantomBridge.getWallet()
        }

        connectButton.setOnClickListener {
            if (phantomBridge.getWallet().isNullOrEmpty()) {
                phantomBridge.connectWallet(
                    redirectScheme,
                    redirectHost,
                    connectionWalletPath,
                    this,
                    getString(R.string.appUrl),
                    packageManager
                ) {
                    Toast.makeText(this, "Phantom app is not installed ", Toast.LENGTH_SHORT).show()
                }
            } else Toast.makeText(this, "App already connected ", Toast.LENGTH_SHORT).show()
        }

        sendTransactionButton.setOnClickListener{
            phantomBridge.sendTransaction(this, packageManager, redirectScheme,
                redirectHost,
                connectionWalletPath,"6A8JZcHYQtysJ6GJh9hFf6apj1ppq1CNYyYTvEK93WLF",{
                    Toast.makeText(this, "Phantom app is not installed ", Toast.LENGTH_SHORT).show()
                })
        }

        disconnectButton.setOnClickListener {
            phantomBridge.disconnectWallet(
                redirectScheme,
                redirectHost,
                disconnectionPath,
                this,
                packageManager
            ) {

            }
        }

        signUtfMessageButton.setOnClickListener {
            phantomBridge.signUtfMessage(
                redirectScheme,
                redirectHost,
                messageSigningPath,
                this,
                packageManager,
                messageEditText.text.toString()
            ) {

            }
        }

        signHexMessageButton.setOnClickListener {
            phantomBridge.signHexMessage(
                redirectScheme,
                redirectHost,
                messageSigningPath,
                this,
                packageManager,
                messageEditText.text.toString()
            ) {

            }
        }
    }

    override fun onResume() {
        super.onResume()
        intent.data?.let {
            Log.d("TEMPODONE", "URII TO PARSE " + it)
            phantomHandler.handleSignMessageData(
                messageSigningPath,
                intent.action.orEmpty(),
                it,
                {

                },
                {

                }
            )
            phantomHandler.handleWalletConnection(
                connectionWalletPath,
                intent.action.orEmpty(),
                it,
                {
                    if (!phantomBridge.getWallet().isNullOrEmpty()) {
                        connectButton.visibility = View.GONE
                        disconnectButton.visibility = View.VISIBLE
                        signUtfMessageButton.visibility = View.VISIBLE
                        signHexMessageButton.visibility = View.VISIBLE
                        messageEditText.visibility = View.VISIBLE
                        walletTextView.visibility = View.VISIBLE
                        walletTextView.text = phantomBridge.getWallet()
                    }
                },
                {

                }
            )
            phantomHandler.handleDisconnect(
                disconnectionPath,
                intent.action.orEmpty(),
                it,
                {
                    connectButton.visibility = View.VISIBLE
                    disconnectButton.visibility = View.GONE
                    signUtfMessageButton.visibility = View.GONE
                    signHexMessageButton.visibility = View.GONE
                    messageEditText.visibility = View.GONE
                    walletTextView.visibility = View.GONE
                },
                {

                }
            )
        }
    }
}
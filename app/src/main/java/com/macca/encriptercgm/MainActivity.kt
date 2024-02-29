package com.macca.encriptercgm
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextLink = findViewById<EditText>(R.id.editText_link)
        val buttonEncrypt = findViewById<Button>(R.id.button_encrypt)
        val textviewEncryptedLink = findViewById<TextView>(R.id.textview_encrypted_link)
        val buttonCopy = findViewById<Button>(R.id.buttonCopy)
        val buttonShare = findViewById<Button>(R.id.buttonShare)
        val buttonPaste = findViewById<Button>(R.id.buttonPaste)

        buttonEncrypt.setOnClickListener {
            val originalLink = editTextLink.text.toString()

            if (isValidUrl(originalLink)) {
                val encryptedLink = encryptLink(originalLink)
                textviewEncryptedLink.text = "$encryptedLink"
            } else {
                showToast("Please enter a valid URL")
            }
        }

        buttonCopy.setOnClickListener {
            val encryptedLink = textviewEncryptedLink.text.toString()
            copyToClipboard(encryptedLink)
            showToast("Link copied to clipboard")
        }

        buttonShare.setOnClickListener {
            val encryptedLink = textviewEncryptedLink.text.toString()
            shareLink(encryptedLink)
        }
        buttonPaste.setOnClickListener{
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = clipboard.primaryClip

            if (clipData != null && clipData.itemCount > 0) {
                val pastedText = clipData.getItemAt(0).text.toString()

                // Set hasil paste ke dalam EditText
                editTextLink.setText(pastedText)
            } else {
                // Clipboard kosong
                Toast.makeText(this, "Clipboard kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidUrl(url: String): Boolean {
        try {
            val uri = URL(url)
            // Check if the host is not empty
            return uri.host != null
        } catch (e: MalformedURLException) {
            // Handle MalformedURLException, for example, by returning false
            return false
        }
    }

    private fun encryptLink(originalLink: String): String {
        val layeredCipher = LayeredCipher()
        val encryptedLink = layeredCipher.encrypt(originalLink)
        Log.d("EncryptedLink", "Original Link: $originalLink, Encrypted Link: $encryptedLink")
        return encryptedLink
    }

    private fun copyToClipboard(text: String) {
        val clipboard =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Encrypted Link", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun shareLink(link: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

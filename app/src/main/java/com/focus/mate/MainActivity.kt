package com.focus.mate

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 80, 40, 40)
            setBackgroundColor(0xFF0F172A.toInt())
        }

        val title = TextView(this).apply {
            text = "Focus mate 69"
            textSize = 28f
            setTextColor(0xFF38BDF8.toInt())
            setPadding(0, 0, 0, 20)
        }

        val desc = TextView(this).apply {
            text = "Regain jaisa focus blocker activate karne ke liye neeche dono zaroori permissions enable karein:"
            textSize = 16f
            setTextColor(0xFFCBD5E1.toInt())
            setPadding(0, 0, 0, 40)
        }

        val btnOverlay = Button(this).apply {
            text = "1. Enable Overlay (Display over apps)"
            setBackgroundColor(0xFF2563EB.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this@MainActivity)) {
                    val intent = Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    )
                    startActivity(intent)
                }
            }
        }

        val btnAccessibility = Button(this).apply {
            text = "2. Enable App Blocker Service"
            setBackgroundColor(0xFF10B981.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            setOnClickListener {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }
        }

        layout.addView(title)
        layout.addView(desc)
        layout.addView(btnOverlay)
        layout.addView(btnAccessibility)

        setContentView(layout)
    }
}

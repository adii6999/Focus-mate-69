package com.focus.mate

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

class FloatingService : Service() {

    private var windowManager: WindowManager? = null
    private var overlayView: View? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showPauseOverlay()
        return START_NOT_STICKY
    }

    private fun showPauseOverlay() {
        if (overlayView != null) return

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
        }

        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_dialog, null)
        val tvTimer = overlayView?.findViewById<TextView>(R.id.tvCountdown)
        val btnContinue = overlayView?.findViewById<Button>(R.id.btnContinue)

        btnContinue?.isEnabled = false

        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secLeft = (millisUntilFinished / 1000) + 1
                tvTimer?.text = "Deep breath... $secLeft s"
                btnContinue?.text = "Wait ${secLeft}s..."
            }

            override fun onFinish() {
                tvTimer?.text = "Kya ye open karna sach me zaroori hai?"
                btnContinue?.isEnabled = true
                btnContinue?.text = "Continue to App"
            }
        }.start()

        btnContinue?.setOnClickListener {
            removeOverlay()
            stopSelf()
        }

        windowManager?.addView(overlayView, params)
    }

    private fun removeOverlay() {
        if (overlayView != null) {
            windowManager?.removeView(overlayView)
            overlayView = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeOverlay()
    }
}

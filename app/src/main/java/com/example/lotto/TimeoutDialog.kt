package com.example.lotto

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager
import androidx.annotation.CallSuper

open class TimeoutDialog(
    c: Context,
    themeResId: Int = 0,
    var timeoutSeconds: Int = DEFAULT_TIME_OUT
) : Dialog(c, themeResId) {

    private var timeoutHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate start")
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate end")
    }

    @CallSuper
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        setTimeout(timeoutSeconds)
        return super.onKeyUp(keyCode, event)
    }

    @CallSuper
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        setTimeout(timeoutSeconds)
        return super.onKeyDown(keyCode, event)
    }

    fun setTimeout(seconds: Int) {
        Log.i(TAG, "setTimeout start:$timeoutSeconds")
        timeoutSeconds = seconds
        timeoutHandler?.removeMessages(1) ?: Log.e(TAG, "timeoutHandler not ready")
        if (seconds > 0) timeoutHandler?.sendEmptyMessageDelayed(1, (seconds * 1000).toLong())
        Log.i(TAG, "setTimeout start")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart")
        timeoutHandler = Handler(Looper.getMainLooper()) {
            Log.i(TAG, "dismiss start")
            dismiss()
            Log.i(TAG, "dismiss end")
            true
        }
        setTimeout(timeoutSeconds)
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop")
        timeoutHandler?.removeCallbacksAndMessages(null)
        timeoutHandler = null
    }

    companion object {
        private val TAG = TimeoutDialog::class.java.simpleName
        const val DEFAULT_TIME_OUT = 120
        const val POPUP_TIME_OUT = 5
        const val MANUAL_SCAN_TIME_OUT = 600
        const val NO_TIME_OUT = 0
    }

    init {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}
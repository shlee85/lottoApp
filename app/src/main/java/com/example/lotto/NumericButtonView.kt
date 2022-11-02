package com.example.lotto

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class NumericButtonView : View, View.OnTouchListener {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        with(
            context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.text))
        ) {
            getText(0).let {
                if (it.isNotEmpty()) strText = it.toString()
            }
            recycle()
        }
        setOnTouchListener(this)
    }

    private val paint = Paint()
    private var strText = "0"
    private var isTouched = false
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.i(TAG, "width:$width height:$height")
        var margin = if (width < height) width / 50 else height / 50
        if (margin < 1) margin = 1
        paint.color = Color.BLACK
        canvas.drawRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(), paint
        )
        paint.color = if (isTouched) Color.GRAY else Color.WHITE
        canvas.drawRect(
            margin.toFloat() * 1,
            margin.toFloat() * 1,
            width.toFloat() - margin.toFloat() * 2,
            height.toFloat() - margin.toFloat() * 2,
            paint
        )
        paint.color = Color.BLACK
        val txt = getText()
        paint.isAntiAlias = true
        paint.isFakeBoldText = true
        paint.textSize = getTextSize(txt, width - margin * 4, height - margin * 4).toFloat()
        val x = (width - paint.measureText(txt)) / 2f
        val y =
            (height - (paint.fontMetrics.bottom - paint.fontMetrics.top)) / 2f - paint.fontMetrics.top
        canvas.drawText(txt, x, y, paint)
    }

    private fun getTextSize(txt: String, maxWidth: Int, maxHeight: Int): Int {
        for (textSize in 1..1000) {
            paint.textSize = textSize.toFloat()
            if (paint.measureText(txt) > maxWidth) return textSize - 1
            val height = (paint.fontMetrics.bottom - paint.fontMetrics.top + 1).toInt()
            if (height > maxHeight) return textSize - 1
        }
        return 1000
    }

    private fun getText(): String {
        if (strText.isEmpty()) return " "
        return strText.substring(0, 1)
    }

    companion object {
        private val TAG = NumericButtonView::class.java.simpleName
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        Log.i(TAG, "$event")
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                isTouched = true
                postInvalidate()
                mListener?.onTouch(getText())
            }
            MotionEvent.ACTION_UP -> {
                isTouched = false
                postInvalidate()
            }
            MotionEvent.ACTION_CANCEL -> {
                isTouched = false
                postInvalidate()
            }
        }
        return true
    }

    interface OnListener {
        fun onTouch(str: String)
    }

    private var mListener: OnListener? = null
    fun setListener(listener: OnListener) {
        mListener = listener
    }
}
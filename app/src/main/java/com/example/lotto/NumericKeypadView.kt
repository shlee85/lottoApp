package com.example.lotto

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lotto.databinding.NumericKeypadBinding

class NumericKeypadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ConstraintLayout(context, attrs) {
    var binding = NumericKeypadBinding.inflate(LayoutInflater.from(context), this, true)
    private var mListener: OnListener? = null

    interface OnListener {
        fun clickEvent(str: String)
    }

    fun setListener(listener: OnListener) {
        mListener = listener
        val touchListener = object : NumericButtonView.OnListener {
            override fun onTouch(str: String) {
                mListener?.clickEvent(str)
            }
        }
        binding.number0.setListener(touchListener)
        binding.number1.setListener(touchListener)
        binding.number2.setListener(touchListener)
        binding.number3.setListener(touchListener)
        binding.number4.setListener(touchListener)
        binding.number5.setListener(touchListener)
        binding.number6.setListener(touchListener)
        binding.number7.setListener(touchListener)
        binding.number8.setListener(touchListener)
        binding.number9.setListener(touchListener)
    }
}
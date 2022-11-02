package com.example.lotto

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.TextView
import com.example.lotto.databinding.ManualCreateNumbersBinding

class ManualCreateNumber(context: Context)
    : TimeoutDialog(context, android.R.style.Theme_NoTitleBar_Fullscreen, NO_TIME_OUT) {

    private lateinit var binding: ManualCreateNumbersBinding
    private var mCnt = 0
    private lateinit var mNumberCode : Array<TextView?>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "start onCreate()")
        super.onCreate(savedInstanceState)

        binding = ManualCreateNumbersBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        /* 사이즈 지정. Array로 정의해야 가능. */
        mNumberCode = arrayOfNulls(7)
        mNumberCode[0] = binding.manualNum1
        mNumberCode[1] = binding.manualNum2
        mNumberCode[2] = binding.manualNum3
        mNumberCode[3] = binding.manualNum4
        mNumberCode[4] = binding.manualNum5
        mNumberCode[5] = binding.manualNum6
        mNumberCode[6] = binding.manualNumBonus
    }

    override fun onStart() {
        Log.d(TAG, "start onStart()")
        super.onStart()

        binding.customNumberKeypad.setListener(object : NumericKeypadView.OnListener{
            override fun clickEvent(str: String) {
                Log.d(TAG, "Custom Keypad click event called. str: $str")
                onKeyUp(str.toInt())
            }
        })
    }

    fun onKeyUp(keycode: Int) {
        Log.d(TAG, "onKeyUp. keycode : $keycode")
        mNumberCode[mCnt++]?.text = keycode.toString()

        Log.d(TAG, "mCnt : $mCnt")
        if(mCnt >= 7) {
            mCnt = 0
            //this.dismiss()
        }
    }

    companion object {
        private val TAG = ManualCreateNumber::class.java.simpleName
    }
}
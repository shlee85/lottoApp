package com.example.lotto

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.lotto.databinding.AutoCreateNumbersBinding

/* android.R.style.Theme_Black_NoTitleBar_Fullscreen 하면 전체화면으로 보여진다. */
class AutoCreateNumber(context: Context, private val numbers: ArrayList<Int>) : TimeoutDialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen, NO_TIME_OUT) {
    private lateinit var binding: AutoCreateNumbersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)

        binding = AutoCreateNumbersBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
    }

    override fun onStart() {
        Log.d(TAG, "[power cycle] onStart")
        super.onStart()

        binding.autoCreateStartBtn.setOnClickListener {
            Log.d(TAG, "Auto create number start!")
            autoCreateNumber(numbers)
        }
    }

    override fun onStop() {
        Log.d(TAG, "")
        super.onStop()
    }

    private var mCnt = 0
    private fun doubleCheck(number: Int, arrayNum: ArrayList<Int>) : Boolean {
        arrayNum.forEach {
            if(number == it) {
                Log.d(TAG, "중복 ( $number : $it )")
                return false
            }
        }
        return true
    }

    private fun autoCreateNumber(numbers: ArrayList<Int>) {
        Log.d(TAG, "Auto...Create")

        /* 랜덤 번호 생성 */
        val range = (1 .. 45)

        while(mCnt < 7) {
            val number = range.random()
            Log.d(TAG, "number[$mCnt] : $number")

            if(doubleCheck(number, numbers)) {
                Log.d(TAG, "데이터가 중복되지 않음")
                numbers.add(number)

                /* 생성된 번호 TextView에 적용 */
                when (mCnt) {
                    0 -> binding.autoNum1.text = number.toString()
                    1 -> binding.autoNum2.text = number.toString()
                    2 -> binding.autoNum3.text = number.toString()
                    3 -> binding.autoNum4.text = number.toString()
                    4 -> binding.autoNum5.text = number.toString()
                    5 -> binding.autoNum6.text = number.toString()
                    6 -> binding.autoNumBonus.text = number.toString()
                }
                mCnt++
            }
        }

        if(mCnt >= 7) {
            mCnt = 0
        }
    }

    companion object {
        private val TAG = AutoCreateNumber::class.java.simpleName
    }
}
package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil.setContentView
import com.example.lotto.databinding.ActivityMainBinding

data class NumbersInfo (
    var number1:Int, var number2:Int, var number3:Int, var number4:Int,
    var number5:Int, var number6:Int, var numberBonus:Int
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mAutoCreateNumber: AutoCreateNumber ?= null
    private var mLottoList: LottoNumberListAdapter ?= null
    private var mManualCreateNumber: ManualCreateNumber ?= null

    private val mNumbers: IntArray = IntArray(8)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottoAutoBtn.setOnClickListener {
            runOnUiThread {
                mAutoCreateNumber = AutoCreateNumber(this, mNumbers)
                mAutoCreateNumber?.show()   //실제 dialog가 호출 된다
                mAutoCreateNumber?.setOnDismissListener {
                    Log.d(TAG, "Auto window dismiss()")
                    mAutoCreateNumber = null
                }

                //Utils.dialogShowWithoutNavigationBar(mAutoCreateNumber)
            }
        }

        binding.lottoListBtn.setOnClickListener {
            runOnUiThread {
                mLottoList = LottoNumberListAdapter(this, mNumbers)
                mLottoList?.show()
                mLottoList?.setOnDismissListener {
                    Log.d(TAG, "LottoNumberList dismiss()")
                    mLottoList = null
                }
            }
        }

        binding.lottoManualBtn.setOnClickListener {
            runOnUiThread {
                mManualCreateNumber = ManualCreateNumber(this)
                mManualCreateNumber?.show()
                mManualCreateNumber?.setOnDismissListener {
                    Log.d(TAG, "manual .. dismiss()")
                    mManualCreateNumber = null
                }
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
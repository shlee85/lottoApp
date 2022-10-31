package com.example.lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.lotto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mAutoCreateNumber: AutoCreateNumber ?= null
    private var mLottoList: LottoNumberListAdapter ?= null

    private lateinit var adapter: LottoNumberListAdapter
    private val mNumbers: ArrayList<Int> = ArrayList()

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
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
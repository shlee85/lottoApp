package com.example.lotto

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.service.autofill.Validators.or
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lotto.databinding.LottoNumberListBinding
import com.example.lotto.databinding.NumberListThemeBinding

class LottoNumberListAdapter(context: Context, private val numbers: IntArray)
    : TimeoutDialog(context, android.R.style.Theme_NoTitleBar_Fullscreen, NO_TIME_OUT) {

    private lateinit var binding: LottoNumberListBinding
    private var mNumberArry : ArrayList<NumbersInfo> = ArrayList()
    private var mIdx = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "life-cycle: start onCreate()")
        super.onCreate(savedInstanceState)

        binding = LottoNumberListBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.listLayout.setOnClickListener {
            Log.d(TAG, "List Closed")
            this.dismiss()
        }

        //numbers.add(1)
        Log.d(TAG, "nums count : ${numbers.size}")
    }

    override fun onStart() {
        Log.d(TAG, "life-cycle: start onStart()")
        super.onStart()

        val file = Environment.getExternalStorageDirectory().absolutePath + "/Lotto/number_list.txt"
        Utils.readTextFile(file, mNumberArry)
        Log.d(TAG, "ArrayList size = ${mNumberArry.size}")

        binding.numberList.layoutManager = LinearLayoutManager(context)
        //binding.numberList.setHasFixedSize(true)
        binding.numberList.adapter = NumberListAdapter(context, numbers)
    }

    override fun onStop() {
        Log.d(TAG, "life-cycle : onStop()")
        super.onStop()
    }

    inner class NumberListAdapter(
        private val context: Context,
        private val numbers: IntArray
    ) : RecyclerView.Adapter<NumberListAdapter.MainViewHolder>() {

        private lateinit var binding: NumberListThemeBinding
        private var numberItem: String = ""

        inner class MainViewHolder(private val binding: NumberListThemeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(idx: Int) {
                Log.d(TAG, "num = ${mNumberArry[idx].number1}")
                binding.itemNumber1.text = mNumberArry[idx].number1.toString()
                binding.itemNumber2.text = mNumberArry[idx].number2.toString()
                binding.itemNumber3.text = mNumberArry[idx].number3.toString()
                binding.itemNumber4.text = mNumberArry[idx].number4.toString()
                binding.itemNumber5.text = mNumberArry[idx].number5.toString()
                binding.itemNumber6.text = mNumberArry[idx].number6.toString()
                binding.itemNumberBonus.text = mNumberArry[idx].numberBonus.toString()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            Log.d(TAG, "start onCreateViewHolder")
            binding = NumberListThemeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            Log.d(TAG, "start onBindViewHolder: $position")
            holder.bind(position)
            mIdx++
        }

        /*
         해당 return값에 따라서 onBindViewHolder 호출 횟수가 정해진다.
         여기서는. return에 대한 값은 DB에서 읽어온 데이터를 기준으로 처리 하는게 맞을 것으로 보임.
        * */
        override fun getItemCount(): Int {
            return mNumberArry.size
        }
    }

    companion object {
        private val TAG = LottoNumberListAdapter::class.java.simpleName
    }
}
package com.example.lotto

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lotto.databinding.LottoNumberListBinding
import com.example.lotto.databinding.NumberListThemeBinding


class LottoNumberListAdapter(context: Context, private val numbers: ArrayList<Int>)
    : TimeoutDialog(context, android.R.style.Theme_NoTitleBar_Fullscreen, NO_TIME_OUT) {

    private lateinit var binding: LottoNumberListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "life-cycle: start onCreate()")
        super.onCreate(savedInstanceState)

        binding = LottoNumberListBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        binding.listLayout.setOnClickListener {
            Log.d(TAG, "List Closed")
            this.dismiss()
        }

        numbers.add(1)
        Log.d(TAG, "nums count : ${numbers.size}")
    }

    override fun onStart() {
        Log.d(TAG, "life-cycle: start onStart()")
        super.onStart()

        binding.numberList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.numberList.setHasFixedSize(true)
        binding.numberList.adapter = NumberListAdapter(context, numbers)
    }

    override fun onStop() {
        Log.d(TAG, "life-cycle : onStop()")
        super.onStop()
    }

    inner class NumberListAdapter(
        private val context: Context,
        private val numbers: ArrayList<Int>
    ) : RecyclerView.Adapter<NumberListAdapter.MainViewHolder>() {

        private lateinit var binding: NumberListThemeBinding

        inner class MainViewHolder(private val binding: NumberListThemeBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind() {
                numbers.forEach {
                    Log.d(TAG, "$it")
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            Log.d(TAG, "start onCreateViewHolder")
            binding = NumberListThemeBinding.inflate(LayoutInflater.from(context), parent, false)
            return MainViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            Log.d(TAG, "start onBindViewHolder")
            holder.bind()
        }

        override fun getItemCount(): Int {
            Log.d(TAG, "count : ${numbers.size}")
            return numbers.size
        }
    }

    companion object {
        private val TAG = LottoNumberListAdapter::class.java.simpleName
    }
}
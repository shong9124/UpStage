package com.capstone2.presentation.view.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone2.domain.model.MyPresentationRecord
import com.capstone2.presentation.databinding.ItemMyPresentationRecordBinding
import com.capstone2.util.LoggerUtil

class MyPresentationRecordRvAdapter(
    private val items: List<MyPresentationRecord>
) : RecyclerView.Adapter<MyPresentationRecordRvAdapter.MyPresentationViewHolder>() {

    inner class MyPresentationViewHolder(val binding: ItemMyPresentationRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyPresentationRecord) {
            LoggerUtil.d("Binding item: ${item.title}")

            binding.tvRecordTitle.text = item.title
            binding.tvRecordScore.text = item.score.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPresentationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyPresentationRecordBinding.inflate(inflater, parent, false)
        return MyPresentationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPresentationViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
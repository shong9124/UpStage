package com.capstone2.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone2.domain.model.home.RecentFeedback
import com.capstone2.presentation.databinding.ItemMyRecentFeedbackBinding
import com.capstone2.util.LoggerUtil

class RecentFeedbackRvAdapter(
    private val items: List<RecentFeedback>
) : RecyclerView.Adapter<RecentFeedbackRvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMyRecentFeedbackBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecentFeedback) {
            LoggerUtil.d("Binding item: ${item.feedback}")

            binding.tvRecentFeedback.text = item.feedback
            binding.tvFeedbackKeyword.text = item.keyword
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyRecentFeedbackBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
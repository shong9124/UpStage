package com.capstone2.presentation.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone2.domain.model.session.GetSessionList
import com.capstone2.presentation.databinding.ItemMyPresentationRecordBinding
import com.capstone2.util.LoggerUtil

class MyRecentPresentationRvAdapter(
    private val items: List<GetSessionList>
) : RecyclerView.Adapter<MyRecentPresentationRvAdapter.MyRecentPresentationViewHolder>() {

    inner class MyRecentPresentationViewHolder(val binding: ItemMyPresentationRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GetSessionList) {
            LoggerUtil.d("Binding item: ${item.title}")

            binding.tvRecordTitle.text = item.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRecentPresentationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyPresentationRecordBinding.inflate(inflater, parent, false)
        return MyRecentPresentationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyRecentPresentationViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
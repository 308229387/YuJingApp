package com.neworange.yujingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neworange.yujingapp.R
import com.neworange.yujingapp.data.WarningData

class ItemAdapter(
    // 关键修改1：将参数改为可变集合
    private val items: MutableList<WarningData>,
    private val onItemClick: (WarningData) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // 关键修改2：添加 DiffUtil 更新方法
    fun updateData(newList: List<WarningData>) {
        val diffCallback = WarningDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    // 关键修改3：实现 DiffUtil 回调类
    private class WarningDiffCallback(
        private val oldList: List<WarningData>,
        private val newList: List<WarningData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        // 通过唯一ID判断是否是同一项
        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos].id == newList[newPos].id
        }

        // 判断内容是否变化（需要数据类实现equals）
        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return oldList[oldPos] == newList[newPos]
        }
    }

    // 以下保持原有代码不变 ▼▼▼
    inner class ItemViewHolder(
        itemView: View,
        private val onItemClick: (WarningData) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvImage: ImageView = itemView.findViewById(R.id.tvImage)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    items.getOrNull(position)?.let { data ->
                        onItemClick(data)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.modelName
        holder.tvTime.text = item.takeTime
        holder.tvLocation.text = item.address
        if(item.type == 122){
            Glide.with(holder.itemView.context)
                .load(R.mipmap.img_tupian)
                .into(holder.tvImage)
        }else{
            Glide.with(holder.itemView.context)
                .load(item.targetSceneWithBox)
                .into(holder.tvImage)
        }

    }

    override fun getItemCount(): Int = items.size
}

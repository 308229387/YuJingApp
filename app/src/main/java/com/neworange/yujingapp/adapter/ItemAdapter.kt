package com.neworange.yujingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neworange.yujingapp.R
import com.neworange.yujingapp.data.Item

class ItemAdapter(private val items: List<Item>) :

    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // ViewHolder 负责绑定布局中的视图
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        val tvImage: ImageView = itemView.findViewById(R.id.tvImage)
    }

    // 创建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view)
    }

    // 绑定数据到 ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.tvTitle.text = item.title
        holder.tvTime.text = item.time
        holder.tvLocation.text = item.location
        Glide.with(holder.itemView.context).load(R.drawable.ic_launcher_background).into(holder.tvImage)
    }

    // 返回列表项总数
    override fun getItemCount(): Int = items.size
}

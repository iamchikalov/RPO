package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.parser3.Item
import com.example.parser3.QueryResult
import com.squareup.picasso.Picasso

class MyListAdapter(
    var items: List<Item>,
    val onItemClickListener: OnItemClick?) : RecyclerView.Adapter<MyListAdapter.ItemViewHolder>(){


    interface OnItemClick{
        fun onItemClick(item: Item)
    }

    fun setNewItems(newItems: QueryResult){
        items = newItems.items
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val description: TextView = itemView.findViewById(R.id.description)
        val image: ImageView = itemView.findViewById(R.id.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.name
        holder.description.text = item.description
        Picasso.get().load(item.owner?.avatar_url).into(holder.image)
        holder.itemView.setOnClickListener{onItemClickListener?.onItemClick(item)}
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
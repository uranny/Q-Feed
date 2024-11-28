package com.example.cardsnap.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.serverDaechae.InChat

class ChatAdapter(private val chatLst: ArrayList<InChat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(chatLst[position].id){
            "string" -> 1
            else -> 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.send_chat, parent, false)
                MineViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.receive_chat, parent, false)
                OtherViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MineViewHolder -> holder.bind(chatLst[position])
            is OtherViewHolder -> holder.bind(chatLst[position])
        }
    }

    override fun getItemCount(): Int = chatLst.size

    class MineViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: InChat){
            itemView.findViewById<TextView>(R.id.sendTxt).text = item.txt
        }
    }

    class OtherViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(item : InChat){
            itemView.findViewById<TextView>(R.id.receiveTxt).text = item.txt
        }
    }
}
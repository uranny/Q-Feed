package com.example.cardsnap.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.adapter.item.Chat
import com.example.cardsnap.data.user.UserInfo.chatLst

class ChatAdapter(private val chatLst: ArrayList<Chat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(chatLst[position].id){
            UserInfo.myId -> 1
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is MineViewHolder -> holder.bind(chatLst[position], position)
            is OtherViewHolder -> holder.bind(chatLst[position], position)
        }
    }

    override fun getItemCount(): Int = chatLst.size

    class MineViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: Chat, pos : Int){
            itemView.findViewById<TextView>(R.id.sendTxt).text = item.txt

            val dateTime = itemView.findViewById<TextView>(R.id.dateTimeTxt)
            when{
                pos == 0 -> {
                    dateTime.visibility = View.VISIBLE
                    dateTime.text = "${item.dateTime.year}년 ${item.dateTime.monthValue}월 ${item.dateTime.dayOfMonth}일"
                }
                chatLst[pos-1].id != item.id -> {
                    dateTime.visibility = View.VISIBLE
                    dateTime.text = "${item.dateTime.year}년 ${item.dateTime.monthValue}월 ${item.dateTime.dayOfMonth}일"
                }
                else -> dateTime.visibility = View.GONE
            }

            val itemTime = itemView.findViewById<TextView>(R.id.timeTxt)
            when{
                pos == chatLst.size-1 -> {
                    itemTime.visibility = View.VISIBLE
                    itemTime.text = "${item.dateTime.hour}:${item.dateTime.minute}"
                }
                chatLst[pos+1].id != item.id -> {
                    itemTime.visibility = View.VISIBLE
                    itemTime.text = "${item.dateTime.hour}:${item.dateTime.minute}"
                }
                else -> itemTime.visibility = View.INVISIBLE
            }
        }
    }

    class OtherViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item : Chat, pos : Int){
            itemView.findViewById<TextView>(R.id.receiveTxt).text = item.txt

            val dateTime = itemView.findViewById<TextView>(R.id.dateTimeTxt)
            when{
                pos == 0 -> {
                    dateTime.visibility = View.VISIBLE
                    dateTime.text = "${item.dateTime.year}년 ${item.dateTime.monthValue}월 ${item.dateTime.dayOfMonth}일"
                }
                chatLst[pos-1].id != item.id -> {
                    dateTime.visibility = View.VISIBLE
                    dateTime.text = "${item.dateTime.year}년 ${item.dateTime.monthValue}월 ${item.dateTime.dayOfMonth}일"
                }
                else -> dateTime.visibility = View.GONE
            }

            val itemTime = itemView.findViewById<TextView>(R.id.timeTxt)
            when{
                pos == chatLst.size-1 -> {
                    itemTime.visibility = View.VISIBLE
                    itemTime.text = "${item.dateTime.hour}:${item.dateTime.minute}"
                }
                chatLst[pos+1].id != item.id -> {
                    itemTime.visibility = View.VISIBLE
                    itemTime.text = "${item.dateTime.hour}:${item.dateTime.minute}"
                }
                else -> itemTime.visibility = View.INVISIBLE
            }
        }
    }
}
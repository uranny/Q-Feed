package com.example.cardsnap.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsnap.activity.InChatActivity
import com.example.cardsnap.R
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.User

class ChatAdapter(private val chatLst: ArrayList<Chat>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImg = itemView.findViewById<ImageView>(R.id.otherUserImg)
        val userSize = itemView.findViewById<ConstraintLayout>(R.id.userSize)
        val userName = itemView.findViewById<TextView>(R.id.otherUserName)
        val lastUserChat = itemView.findViewById<TextView>(R.id.lastChat)
        val lastTime = itemView.findViewById<TextView>(R.id.lastChatTime)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_chat_list, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        try{
            val chat = chatLst[position]
            val imageUri = chat.userImg

            holder.lastTime.setText(chat.time)

            if(chat.chatList.isEmpty()){
                holder.lastUserChat.visibility = View.INVISIBLE
            }
            else{
                holder.lastUserChat.setText(chat.lastChat)
                holder.lastUserChat.visibility = View.VISIBLE
            }

            holder.userName.setText(chat.userName)

            if (imageUri.isNotEmpty()) {
                // Glide로 이미지 로드
                Glide.with(holder.itemView.context)
                    .load(Uri.parse(imageUri)) // content:// URI를 Uri 객체로 변환
                    .error(R.drawable.img_4)   // 에러 시 표시할 이미지
                    .into(holder.userImg)      // 이미지를 ImageView에 로드
            } else {
                // 기본 이미지 설정
                holder.userImg.setImageResource(R.drawable.ic_launcher_foreground)
            }
        } catch (e : Exception){
            Log.e("MyTag", "${e.message}")
        }

        holder.userSize.setOnClickListener {

            User.userChatIndex = position

            val context = holder.itemView.context
            val intent = Intent(context, InChatActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chatLst.size
    }
}
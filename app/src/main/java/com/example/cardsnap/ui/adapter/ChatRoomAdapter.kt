package com.example.cardsnap.ui.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.databinding.UserChatListBinding
import com.example.cardsnap.ui.adapter.item.ChatRoom

class ChatRoomAdapter(private val chatRoomLst: ArrayList<ChatRoom>, private val navController: NavController) : RecyclerView.Adapter<ChatRoomAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(val binding: UserChatListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = UserChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val binding = holder.binding
        try{
            val chat = chatRoomLst[position]
            val imageUri = chat.userImg

            binding.lastChatTime.setText(chat.time)

            if(chat.lastChat == null){
                binding.lastChat.visibility = View.INVISIBLE
            }
            else{
                binding.lastChat.text = chat.lastChat
                binding.lastChat.visibility = View.VISIBLE
            }

            binding.otherUserName.setText(chat.userName)

            if (imageUri.isNotEmpty()) {
                // Glide로 이미지 로드
                Glide.with(holder.itemView.context)
                    .load(Uri.parse(imageUri)) // content:// URI를 Uri 객체로 변환
                    .error(R.drawable.img_4)   // 에러 시 표시할 이미지
                    .into(binding.otherUserImg)      // 이미지를 ImageView에 로드
            } else {
                // 기본 이미지 설정
                binding.otherUserImg.setImageResource(R.drawable.ic_launcher_foreground)
            }
        } catch (e : Exception){
            Log.e("MyTag", "${e.message}")
        }

        binding.userSize.setOnClickListener {
            navController.navigate(R.id.action_fragChat_to_fragInChat)
        }
    }

    override fun getItemCount(): Int {
        return chatRoomLst.size
    }
}
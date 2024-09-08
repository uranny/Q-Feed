package com.example.cardsnap.adapter

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsnap.activity.InChatActivity
import com.example.cardsnap.activity.OtherActivity
import com.example.cardsnap.serverDaechae.Post
import com.example.cardsnap.R
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostAdapter(private val postLst: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName = itemView.findViewById<TextView>(R.id.userName)
        val userAffil = itemView.findViewById<TextView>(R.id.userAffil)
        val timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
        val userImg = itemView.findViewById<ImageView>(R.id.userImg)
        val likeTxt = itemView.findViewById<TextView>(R.id.likeTxt)
        val titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
        val tagTxt = itemView.findViewById<TextView>(R.id.hashTag)
        val chatBtn = itemView.findViewById<ImageView>(R.id.chatBtn)
        val likeBtn = itemView.findViewById<ImageView>(R.id.likeBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return PostViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postLst[position]
        val userId = post.userId
        val imageUri = post.userImg

        Glide.with(holder.itemView.context)
            .load(Uri.parse(imageUri)) // content:// URI를 Uri 객체로 변환
            .error(R.drawable.img_4)   // 에러 시 표시할 이미지
            .into(holder.userImg)      // 이미지를 ImageView에 로드

        Log.d("setImg", "이미지 호출 ${imageUri}")

        holder.userName.text = post.userName
        holder.userAffil.text = post.userAffil
        holder.timeTxt.text = post.time
        holder.likeTxt.text = post.list.size.toString()
        holder.titleTxt.text = post.messagetxt

        // 좋아요 버튼 상태 설정
        if (User.userIdLst[User.userLogInIndex] in post.list) {
            holder.likeBtn.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            holder.likeBtn.setImageResource(R.drawable.favorit_normal_btn_img)
        }

        // 유저 이름 클릭 리스너
        holder.userName.setOnClickListener {
            for (i in User.postLst) {
                if (i.userId == userId) {
                    User.userProfileIndex = User.postLst.indexOf(i)
                    break
                }
            }
            val context = holder.itemView.context
            val intent = Intent(context, OtherActivity::class.java)
            context.startActivity(intent)
        }

        holder.userImg.setOnClickListener {
            for (i in User.postLst) {
                if (i.userId == userId) {
                    User.userProfileIndex = User.postLst.indexOf(i)
                    break
                }
            }
            val context = holder.itemView.context
            val intent = Intent(context, OtherActivity::class.java)
            context.startActivity(intent)
        }

        // 채팅 버튼 클릭 리스너
        holder.chatBtn.setOnClickListener {

            checkChat(holder, position)

        }

        // 좋아요 버튼 클릭 리스너
        holder.likeBtn.setOnClickListener {
            val currentUserId = User.userIdLst[User.userLogInIndex]
            if (currentUserId !in post.list) {
                post.list.add(currentUserId)
                holder.likeBtn.setImageResource(R.drawable.baseline_favorite_24)
            } else {
                post.list.remove(currentUserId)
                holder.likeBtn.setImageResource(R.drawable.favorit_normal_btn_img)
            }
            holder.likeTxt.text = post.list.size.toString()
        }

        holder.tagTxt.text = post.tagTxt
    }

    override fun getItemCount(): Int {
        return postLst.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkChat(holder: PostViewHolder, position: Int){

        val post = postLst[position]

        User.userChatIndex = -1

        if(User.userChatLst[User.userLogInIndex].isEmpty()){
            addChat(holder, position)
        }

        for(i in User.userChatLst[User.userLogInIndex]){
            if (i.userId == post.userId){
                User.userChatIndex = User.userChatLst[User.userLogInIndex].indexOf(i)
            }
        }

        if (User.userChatIndex == -1){
            addChat(holder, position)
            User.userChatIndex = 0
        }

        //InChatActivity로 이동하는 코드
        val context = holder.itemView.context
        val intent = Intent(context, InChatActivity::class.java)
        context.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addChat(holder: PostViewHolder, position: Int){
        val post = postLst[position]

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH시 mm분") // 예상 문제 구간
        val formatted = current.format(formatter)

        User.userChatLst[User.userLogInIndex].add(0,
            Chat(
                post.userName,
                post.userImg,
                post.userAffil,
                post.userId,
                formatted
            )
        )
    }
}

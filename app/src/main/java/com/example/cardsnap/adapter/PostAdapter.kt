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
import com.example.cardsnap.databinding.UserListBinding
import com.example.cardsnap.serverDaechae.Chat
import com.example.cardsnap.serverDaechae.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostAdapter(private val postLst: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: UserListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = UserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    // onCreate랑 같은 역할
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val binding = holder.binding
        val post = postLst[position]
        val imageUri = post.userImg

        Glide.with(holder.itemView.context)
            .load(Uri.parse(imageUri)) // content:// URI를 Uri 객체로 변환
            .error(R.drawable.img_4)   // 에러 시 표시할 이미지
            .into(binding.userImg)      // 이미지를 ImageView에 로드

        Log.d("setImg", "이미지 호출 ${imageUri}")

        // 좋아요 버튼 상태 설정
//        if (User.idLst[User.logInIndex] in post.list) {
//            binding.likeBtn.setImageResource(R.drawable.baseline_favorite_24)
//        } else {
//            binding.likeBtn.setImageResource(R.drawable.favorit_normal_btn_img)
//        }

        with(binding){
            userName.text = post.userName
            userAffil.text = post.userAffil
            timeTxt.text = post.time
            likeTxt.text = post.list.size.toString()
            titleTxt.text = post.messagetxt
            tagTxt.text = post.tagTxt
        }

        setClickListeners(binding, holder, post, position)
    }

    // 리스트 사이즈 확인
    override fun getItemCount(): Int {
        return postLst.size
    }

    // ClickListener 관리
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClickListeners(binding : UserListBinding, holder: PostViewHolder, post : Post, position: Int){
        with(binding){
            // 유저 이름 클릭 리스너
            userName.setOnClickListener { inOtherProfile(holder, post) }

            // 유저 이미지 클릭 리스너
            userImg.setOnClickListener { inOtherProfile(holder, post) }

            // 채팅 버튼 클릭 리스너
            chatBtn.setOnClickListener { checkChat(holder, position) }

            // 좋아요 버튼 클릭 리스너
            likeBtn.setOnClickListener { likeBtn(binding, post) }
        }
    }

    // 좋아요 개수 설정
    private fun likeBtn(binding: UserListBinding, post: Post){
//        val currentUserId = User.idLst[User.logInIndex]
//        if (currentUserId !in post.list) {
//            post.list.add(currentUserId)
//            binding.likeBtn.setImageResource(R.drawable.baseline_favorite_24)
//        } else {
//            post.list.remove(currentUserId)
//            binding.likeBtn.setImageResource(R.drawable.favorit_normal_btn_img)
//        }
//        binding.likeTxt.text = post.list.size.toString()
    }

    // 다른 유저 프로필 보기
    private fun inOtherProfile(holder: PostViewHolder, post: Post){

//        try{
//            val userId = post.userId
//
//            for (i in User.postLst) {
//                if (i.userId == userId) {
//                    User.profileIndex = User.postLst.indexOf(i)
//                    break
//                }
//            }
//
//            val context = holder.itemView.context
//            val intent = Intent(context, OtherActivity::class.java)
//            context.startActivity(intent)
//        } catch(e: Exception){
//            Log.e("myTag", "bug ${e.message}")
//        }
    }

    // chat이 있는지 없는 확인
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkChat(holder: PostViewHolder, position: Int){
//
//        val post = postLst[position]
//
//        User.chatIndex = -1
//
//        if(User.chatLst[User.logInIndex].isEmpty()){
//            addChat(position)
//        }
//
//        for(i in User.chatLst[User.logInIndex]){
//            if (i.userId == post.userId){
//                User.chatIndex = User.chatLst[User.logInIndex].indexOf(i)
//            }
//        }
//
//        if (User.chatIndex == -1){
//            addChat(position)
//            User.chatIndex = 0
//        }
//
//        val context = holder.itemView.context
//        val intent = Intent(context, InChatActivity::class.java)
//        context.startActivity(intent)
    }

    // chat 추가하기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addChat(position: Int){
//        val post = postLst[position]
//
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofPattern("HH시 mm분") // 예상 문제 구간
//        val formatted = current.format(formatter)
//
//        User.chatLst[User.logInIndex].add(0,
//            Chat(
//                post.userName,
//                post.userImg,
//                post.userAffil,
//                post.userId,
//                formatted
//            )
//        )
    }
}

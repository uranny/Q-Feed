package com.example.cardsnap.adapter

import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsnap.adapter.adapter_class.Post
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.UserListBinding

class PostAdapter(private val postLst: ArrayList<Post>,
                  private val navController: NavController,
                  private val onItemClicked: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

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
            .error(R.drawable.img_5)   // 에러 시 표시할 이미지
            .into(binding.userImg)      // 이미지를 ImageView에 로드

        Log.d("setImg", "이미지 호출 ${imageUri}")

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
            userName.setOnClickListener {
                UserInfo.otherIndex = position
                navController.navigate(R.id.action_fragHome_to_fragOther)
            }

            // 채팅 버튼 클릭 리스너
            chatBtn.setOnClickListener { onItemClicked(post) }

            // 좋아요 버튼 클릭 리스너
            likeBtn.setOnClickListener { likeBtn(binding, post) }
        }
    }

    // 좋아요 개수 설정
    private fun likeBtn(binding: UserListBinding, post: Post){
        binding.likeBtn.setImageResource(R.drawable.baseline_favorite_24)
    }

    // 다른 유저 프로필 보기
    private fun inOtherProfile(holder: PostViewHolder, post: Post){
    }

    // chat이 있는지 없는 확인
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkChat(holder: PostViewHolder, position: Int){
    }

    // chat 추가하기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addChat(position: Int){
    }
}

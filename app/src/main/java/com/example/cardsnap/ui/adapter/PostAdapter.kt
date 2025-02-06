package com.example.cardsnap.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.adapter.item.Post
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.UserListBinding

class PostAdapter(private val postLst: ArrayList<Post>,
                  private val onViewClicked : (Post) -> Unit,
                  private val onChatClicked: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

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
        binding.uiState = post

        if(post.bookMarkLst.contains(UserInfo.user.id!!)){
            binding.likeBtn.isSelected = true
        } else{
            binding.likeBtn.isSelected = false
        }

        setClickListeners(binding, post)
    }

    // 리스트 사이즈 확인
    override fun getItemCount(): Int {
        return postLst.size
    }

    // ClickListener 관리
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClickListeners(binding : UserListBinding, post : Post){
        with(binding){
            // 유저 이름 클릭 리스너
            userName.setOnClickListener { onViewClicked(post) }

            // 채팅 버튼 클릭 리스너
            chatBtn.setOnClickListener { onChatClicked(post) }

            // 좋아요 버튼 클릭 리스너
            likeBtn.setOnClickListener { likeBtn(binding, post) }
        }
    }

    // 좋아요 개수 설정
    private fun likeBtn(binding: UserListBinding, post: Post){
        binding.likeBtn.isSelected = !binding.likeBtn.isSelected
        if(binding.likeBtn.isSelected){
            post.bookMarkLst.add(UserInfo.user.id!!)
            binding.likeTxt.text = "${post.bookMarkLst.size}개의 좋아요"
        }else{
            if (post.bookMarkLst.contains(UserInfo.user.id!!)){
                post.bookMarkLst.remove(UserInfo.user.id)
                binding.likeTxt.text = "${post.bookMarkLst.size}개의 좋아요"
            } else{
                return
            }
        }
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

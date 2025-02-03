package com.example.cardsnap.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsnap.adapter.item.Post
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
        val imageUri = post.imageUrl

        val arrayMap = mapOf(
            "FIRST_GRADE" to "1학년",
            "SECOND_GRADE" to "2학년",
            "THIRD_GRADE" to "3학년",
        )

        Glide.with(holder.itemView.context)
            .load(imageUri) // content:// URI를 Uri 객체로 변환
            .error(R.drawable.img_5)   // 에러 시 표시할 이미지
            .into(binding.userImg)      // 이미지를 ImageView에 로드

        with(binding){
            userName.text = post.username ?: "string"
            userAffil.text = "${if(post.affiliation == "UNKNOWN") "대구소마고" else post.affiliation} ${arrayMap[post.grade] ?: "1학년"}"
            titleTxt.text = post.statusMessage ?: "string"
            tagTxt.text = post.hashtags?.joinToString(" ") { "#$it" } ?: "#string"
            likeTxt.text = "${post.bookMarkLst.size}개의 좋아요"
        }

        if(post.bookMarkLst.contains(UserInfo.myId!!)){
            binding.likeBtn.isSelected = true
            binding.likeTxt.text = "${post.bookMarkLst.size}개의 좋아요"
        } else{
            binding.likeBtn.isSelected = false
            binding.likeTxt.text = "${post.bookMarkLst.size}개의 좋아요"
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
        binding.likeBtn.isSelected = !binding.likeBtn.isSelected
        if(binding.likeBtn.isSelected){
            post.bookMarkLst.add(UserInfo.myId!!)
            binding.likeTxt.text = "${post.bookMarkLst.size}개의 좋아요"
        }else{
            if (post.bookMarkLst.contains(UserInfo.myId!!)){
                post.bookMarkLst.remove(UserInfo.myId)
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

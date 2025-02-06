package com.example.cardsnap.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.adapter.PostAdapter
import com.example.cardsnap.adapter.item.Post
import com.example.cardsnap.adapter.item.toUser
import com.example.cardsnap.data.repository.RequestManager
import com.example.cardsnap.data.user.User
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.databinding.FrameHomeBinding
import com.example.cardsnap.vm.HomeViewModel
import kotlinx.coroutines.launch

class FragHome : androidx.fragment.app.Fragment() {

    private lateinit var postAdapter: PostAdapter
    private lateinit var binding: FrameHomeBinding
    private val viewModel : HomeViewModel by activityViewModels()
    private var isLoading : Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frame_home, container, false)

        val postView = binding.recycleView
        val userLst = UserInfo.postLst

        val navController = findNavController()

        postAdapter = PostAdapter(userLst,
            onViewClicked = { post ->
                val newUser = post.toUser()
                viewModel.changeUser(newUser)
                navController.navigate(R.id.action_fragHome_to_fragInProfile)
            },
            onChatClicked = { postItem ->
                showBottomSheet(postItem)
            }
        )

        postView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        postView.adapter = postAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(postView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (!isLoading && lastVisibleItemPosition == UserInfo.postLst.size - 1) {
                    try {
                        isLoading = true
                        getArticles(postAdapter,  UserInfo.postLst.size)
                        Log.d("mine", "${UserInfo.postLst.size}")
                    } catch (e : Exception){
                        Log.d("paging", "$e")
                        Toast.makeText(requireContext(), "가져오는데 실패함", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    // 게시글 가져오기
    private fun getArticles(postAdapter: PostAdapter, oldLstSize : Int){
        lifecycleScope.launch {
            try {
                val articlesRsp = UserInfo.accessToken?.let { RequestManager.articlesRequest(it) }
                articlesRsp?.body()?.forEach { articles ->
                    val post =
                        UserInfo.accessToken?.let { token -> RequestManager.getUserInfoRequest(token, articles).body() }
                    val response = Post(
                        post!!.id,
                        post.uid,
                        post.username,
                        post.affiliation,
                        post.grade,
                        post.imageUrl,
                        post.statusMessage,
                        post.hashtags,
                        post.age,
                        post.height,
                        post.weight,
                        post.habbies,
                        post.likes,
                        post.dislikes,
                        post.idealType,
                        arrayListOf(),
                        arrayListOf()
                    )
                    UserInfo.postLst.add(response)
                }
                val addItemCount = articlesRsp?.body()?.size ?: 0
                postAdapter.notifyItemRangeInserted(oldLstSize, addItemCount)
            } catch (e : retrofit2.HttpException){
                Log.e("mine", "${e.message}")
            } catch (e : Exception){
                Log.e("mine", "${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    private fun showBottomSheet(postItem : Post) {
        val bottomSheet = CmtBottomSheet(postItem)
        bottomSheet.show(childFragmentManager, "Tag")
    }
}

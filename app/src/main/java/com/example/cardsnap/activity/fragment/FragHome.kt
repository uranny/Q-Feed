package com.example.cardsnap.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.adapter.PostAdapter
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.user.addPost
import com.example.cardsnap.databinding.FrameHomeBinding
import com.example.cardsnap.serverDaechae.Post

class FragHome : androidx.fragment.app.Fragment() {

    private lateinit var postAdapter: PostAdapter
    private lateinit var binding: FrameHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FrameHomeBinding.inflate(inflater)

        val postView = binding.recycleView
        val userLst = UserInfo.postLst

        val navController = findNavController()

        postAdapter = PostAdapter(userLst, navController) { postItem -> showBottomSheet() }
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
                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

                if (firstVisibleItemPosition == (UserInfo.postLst.size - 1)) {
                    addPost()
                    postAdapter.notifyItemInserted(UserInfo.postLst.size - 1) // 추가된 항목만 갱신
                }
            }
        })
    }

    private fun showBottomSheet() {
        val bottomSheet = CmtBottomSheet()
        bottomSheet.show(childFragmentManager, "Tag")
    }

}

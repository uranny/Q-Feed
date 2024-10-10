package com.example.cardsnap.activity.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.adapter.PostAdapter
import com.example.cardsnap.data.user.UserInfo
import com.example.cardsnap.data.user.addPost
import com.example.cardsnap.serverDaechae.Post

class FragHome : Fragment() {

    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameHome = inflater.inflate(R.layout.frame_home, container, false)

        val postView = frameHome.findViewById<RecyclerView>(R.id.recycleView)
        val userLst = UserInfo.postLst

        postAdapter = PostAdapter(userLst)
        postView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postView.adapter = postAdapter

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(postView)

        postView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()

                Toast.makeText(context, "$firstVisibleItemPosition", Toast.LENGTH_SHORT).show()

                if (firstVisibleItemPosition == (UserInfo.postLst.size - 1)) {
                    addPost()
                    postAdapter.notifyItemInserted(UserInfo.postLst.size - 1) // 추가된 항목만 갱신
                }
            }
        })

        return frameHome
    }
}

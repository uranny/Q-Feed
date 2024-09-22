package com.example.cardsnap.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.serverDaechae.Post
import com.example.cardsnap.R
import com.example.cardsnap.serverDaechae.User
import com.example.cardsnap.adapter.PostAdapter

class FragHome : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameHome = inflater.inflate(R.layout.frame_home, container, false)

        val postView = frameHome.findViewById<RecyclerView>(R.id.recycleView)
        val userLst = arrayListOf<Post>()

        for(i in User.postLst){
            if(i.userId != User.idLst[User.logInIndex]){
                userLst.add(i)
            }
        }

        postView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postView.setHasFixedSize(true)
        postView.adapter = PostAdapter(userLst)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(postView)

        return frameHome
    }
}
package com.example.cardsnap.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.R
import com.example.cardsnap.adapter.PostAdapter
import com.example.cardsnap.data.user.UserInfo

class FragHome : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameHome = inflater.inflate(R.layout.frame_home, container, false)

        val postView = frameHome.findViewById<RecyclerView>(R.id.recycleView)
        val userLst = UserInfo.postLst
//
//        for(i in User.postLst){
//            if(i.userId != User.idLst[User.logInIndex]){
//                userLst.add(i)
//            }
//        }

        postView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        postView.adapter = PostAdapter(userLst)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(postView)

        postView.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()
                val firstCompleteVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstCompletelyVisibleItemPosition()
                val lastCompleteVisibleItemPosition =  (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastCompletelyVisibleItemPosition()
                val lastVisibleItemPosition =  (recyclerView.layoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()

                Log.d("mine", "${firstVisibleItemPosition}\n${firstCompleteVisibleItemPosition}\n${lastVisibleItemPosition}\n${lastCompleteVisibleItemPosition}")
            }
        })


        return frameHome
    }
}
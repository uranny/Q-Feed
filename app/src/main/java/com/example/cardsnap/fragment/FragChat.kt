package com.example.cardsnap.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.adapter.ChatRoomAdapter
import com.example.cardsnap.R
import com.example.cardsnap.data.user.UserInfo

class FragChat : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameChat = inflater.inflate(R.layout.frame_chat, container, false)

        val navController = findNavController()

        try {
            val chatView = frameChat.findViewById<RecyclerView>(R.id.recyclerView)
            val chatLst = UserInfo.chatRoomLst
            chatView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            chatView.setHasFixedSize(true)
            chatView.adapter = ChatRoomAdapter(chatLst, navController)
        } catch (e : Exception){
            Log.e("MyTag", "${e.message}")
        }
        return frameChat
    }
}
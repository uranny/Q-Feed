package com.example.cardsnap.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardsnap.adapter.ChatAdapter
import com.example.cardsnap.databinding.FrameInChatBinding
import com.example.cardsnap.adapter.item.Chat
import com.example.cardsnap.data.user.UserInfo.chatLst
import java.time.LocalDateTime

class FragInChat : Fragment() {

    private lateinit var binding : FrameInChatBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FrameInChatBinding.inflate(inflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ChatAdapter(chatLst)
        binding.chatRecyclerView.adapter = adapter
        binding.chatRecyclerView.post {
            binding.chatRecyclerView.scrollToPosition(chatLst.size - 1)
        }

        binding.sendBtn.setOnClickListener {
            val newMessage = binding.editTxt.text?.toString()?.trim()
            if (!newMessage.isNullOrEmpty()) {
                chatLst.add(Chat(1, newMessage, LocalDateTime.now()))
                adapter.notifyItemInserted(chatLst.size - 1)
                adapter.notifyItemChanged(chatLst.size - 2 )// 추가된 항목만 업데이트
                binding.chatRecyclerView.scrollToPosition(chatLst.size - 1) // 마지막으로 스크롤
                binding.editTxt.text?.clear() // 입력 필드 초기화
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
package com.example.cardsnap.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardsnap.adapter.CmtAdapter
import com.example.cardsnap.databinding.CommentBottomBinding
import com.example.cardsnap.adapter.item.Cmt
import com.example.cardsnap.adapter.item.Post
import com.example.cardsnap.data.user.UserInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime

class CmtBottomSheet(val postItem : Post) : BottomSheetDialogFragment() {

    lateinit var binding : CommentBottomBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = CommentBottomBinding.inflate(inflater)

        val cmtAdapter = CmtAdapter(postItem.chatLst)
        val arrayMap = mapOf(
            "FIRST_GRADE" to "1학년",
            "SECOND_GRADE" to "2학년",
            "THIRD_GRADE" to "3학년",
        )

        binding.commentRecyclerView.layoutManager =
            LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        binding.commentRecyclerView.setHasFixedSize(true)
        binding.commentRecyclerView.adapter = cmtAdapter

        binding.sendBtn.setOnClickListener{
            val comment = binding.editTxt.text?.toString()?.trim()
            Log.d("cmt", "$comment")
            if (!comment.isNullOrEmpty()){
                postItem.chatLst.add(
                    0,
                    Cmt(
                        UserInfo.user.imageUrl ?: "string",
                        UserInfo.user.username ?: "string",
                        "${if(UserInfo.user.affiliation == "UNKNOWN") "대구소마고" else UserInfo.user.affiliation} ${UserInfo.user.grade}",
                        comment,
                        LocalDateTime.now()
                    )
                )
                cmtAdapter.notifyItemInserted(0)
                binding.commentRecyclerView.scrollToPosition(0)
                binding.editTxt.text?.clear() // 입력 필드 초기화
            }
        }

        return binding.root
    }

}
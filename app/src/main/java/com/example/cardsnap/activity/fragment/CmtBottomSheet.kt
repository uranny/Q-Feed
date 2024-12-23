package com.example.cardsnap.activity.fragment

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
import com.example.cardsnap.adapter.adapter_class.Cmt
import com.example.cardsnap.data.user.UserInfo.cmtLst
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime

class CmtBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding : CommentBottomBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = CommentBottomBinding.inflate(inflater)

        val cmtAdapter = CmtAdapter(cmtLst)

        binding.commentRecyclerView.layoutManager =
            LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        binding.commentRecyclerView.setHasFixedSize(true)
        binding.commentRecyclerView.adapter = cmtAdapter

        binding.sendBtn.setOnClickListener{
            val comment = binding.editTxt.text?.toString()?.trim()
            Log.d("cmt", "$comment")
            if (!comment.isNullOrEmpty()){
                cmtLst.add(
                    0,
                    Cmt(
                        "아마",
                        "string",
                        "string",
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
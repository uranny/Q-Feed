package com.example.cardsnap.activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cardsnap.R
import com.example.cardsnap.adapter.CmtAdapter
import com.example.cardsnap.databinding.CommentBottomBinding
import com.example.cardsnap.serverDaechae.Cmt
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.w3c.dom.Comment

class CmtBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding : CommentBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = CommentBottomBinding.inflate(inflater)
        val cmtLst = arrayListOf<Cmt>(
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha"),
            Cmt("아마", "string", "string", "jehahaha")
            )

        val cmtAdapter = CmtAdapter(cmtLst)

        binding.commentRecyclerView.layoutManager =
            LinearLayoutManager(container?.context, LinearLayoutManager.VERTICAL, false)
        binding.commentRecyclerView.setHasFixedSize(true)
        binding.commentRecyclerView.adapter = cmtAdapter

        binding.sendBtn.setOnClickListener{
            if (!(binding.commentTxt.text.isNullOrEmpty())){
                cmtLst.add(0, Cmt("아마", "string", "string", binding.commentTxt.text.toString()))
                cmtAdapter.notifyItemInserted(0)
                binding.commentRecyclerView.scrollToPosition(0)
                binding.editTxt.text?.clear() // 입력 필드 초기화
            }
        }

        return binding.root
    }

}
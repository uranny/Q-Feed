package com.example.cardsnap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cardsnap.databinding.CmtItemBinding
import com.example.cardsnap.databinding.UserListBinding
import com.example.cardsnap.serverDaechae.Cmt

class CmtAdapter(private val cmtLst : ArrayList<Cmt>) : RecyclerView.Adapter<CmtAdapter.CmtViewHolder>() {
    inner class CmtViewHolder(val binding: CmtItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CmtViewHolder {
        val binding = CmtItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CmtViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CmtViewHolder, position: Int) {
        val binding = holder.binding
    }

    override fun getItemCount(): Int = cmtLst.size
}
package com.example.cardsnap.presentation.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import com.example.cardsnap.databinding.CmtItemBinding
import com.example.cardsnap.domain.entity.item.Cmt
import java.time.LocalDateTime
import java.time.Duration

class CmtAdapter(private val cmtLst : ArrayList<Cmt>) : RecyclerView.Adapter<CmtAdapter.CmtViewHolder>() {
    inner class CmtViewHolder(val binding: CmtItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CmtViewHolder {
        val binding = CmtItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CmtViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: CmtViewHolder, position: Int) {
        val binding = holder.binding
        val user = cmtLst[position]
        binding.userName.text = user.userName
        binding.userAffil.text = user.userAffil
        binding.cmtTxt.text = user.cmtTxt

        Glide.with(holder.itemView.context)
            .load(user.userImg) // content:// URI를 Uri 객체로 변환
            .error(R.drawable.img_5)   // 에러 시 표시할 이미지
            .into(binding.userImg)      // 이미지를 ImageView에 로드


        val duration = Duration.between(user.time, LocalDateTime.now())

        binding.time.text = beforeTime(duration)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun beforeTime(duration: Duration): String {
        when{
            duration.toDays() >= 365 -> return "${(duration.toDays()/365).toInt()}년 전"
            duration.toDays() >= 30L -> return "${(duration.toDays()/30).toInt()}달 전"
            duration.toDays() != 0L -> return "${duration.toDays()%30}일 전"
            duration.toHours() != 0L -> return "${duration.toHours()}시간 전"
            duration.toMinutes() != 0L -> return "${duration.toMinutes()}분 전"
            else -> return "${duration.toSeconds()}초 전"
        }
    }

    override fun getItemCount(): Int = cmtLst.size
}
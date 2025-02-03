package com.example.cardsnap.adapter.bind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cardsnap.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.img_5)
            .into(imageView)
    }
}
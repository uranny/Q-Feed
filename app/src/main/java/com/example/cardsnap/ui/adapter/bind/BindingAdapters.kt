package com.example.cardsnap.ui.adapter.bind

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cardsnap.R
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .load(url)
            .error(R.drawable.img_5)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("affiliation", "grade")
    fun setAffiliation(textView: TextView, affiliation: String?, grade : String?) {
        val affililiationText = if (affiliation == "UNKNOWN") "대구소마고" else affiliation
        val arrayMap = mapOf(
            "FIRST_GRADE" to "1학년",
            "SECOND_GRADE" to "2학년",
            "THIRD_GRADE" to "3학년",
        )
        val gradeText = arrayMap[grade] ?: "1학년"

        textView.text = "$affililiationText $gradeText"
    }

    @JvmStatic
    @BindingAdapter("ui_text")
    fun setText(view: TextView, text : String?) {
        val defaultText = view.context.getString(R.string.default_text)
        view.text = text ?: defaultText
    }

    @JvmStatic
    @BindingAdapter("hashtags")
    fun setHashtags(view: TextView, hashtags: List<String>?) {
        val defaultText = view.context.getString(R.string.default_text)
        if(hashtags.isNullOrEmpty()){
            view.text = "#$defaultText"
        } else{
            val text = hashtags.joinToString(" ") { "#$it" }
            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("bookmark")
    fun setBookmark(view: TextView, bookmarks: List<Int>?) {
        view.text = "${bookmarks?.size ?: 0}개의 좋아요"
    }

    @JvmStatic
    @BindingAdapter("edit_text")
    fun setEditText(textView: EditText, textFlow : StateFlow<String>?) {
        val lifecycleOwner = textView.context as? LifecycleOwner ?: return

        lifecycleOwner.lifecycleScope.launch {
            textFlow?.collect{ newText ->
                if (textView.text.toString() != newText) { // 불필요한 업데이트 방지
                    textView.setText(newText)
                    textView.setSelection(newText.length) // 커서를 끝으로 이동
                }
            }
        }
    }
}
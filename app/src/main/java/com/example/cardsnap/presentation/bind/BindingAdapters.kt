package com.example.cardsnap.presentation.bind

import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cardsnap.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun ImageView.bindImage( url: String?) {
        Glide.with(this.context)
            .load(url)
            .error(R.drawable.img_5)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("affiliation", "grade")
    fun TextView.setAffiliation(affiliation: String?, grade : String?) {
        val affiliationText = if (affiliation == "UNKNOWN") "대구소마고" else affiliation
        val arrayMap = mapOf(
            "FIRST_GRADE" to "1학년",
            "SECOND_GRADE" to "2학년",
            "THIRD_GRADE" to "3학년",
        )
        val gradeText = arrayMap[grade] ?: "1학년"
        
        val finalText = "$affiliationText $gradeText"

        this.text = finalText
    }

    @JvmStatic
    @BindingAdapter("ui_text")
    fun TextView.setUiText(text : String?) {
        val defaultText = this.context.getString(R.string.default_text)
        this.text = text ?: defaultText
    }

    @JvmStatic
    @BindingAdapter("hashtags")
    fun TextView.setHashtags( hashtags: List<String>?) {
        val defaultText = "#" + this.context.getString(R.string.default_text)
        if(hashtags.isNullOrEmpty()){
            this.text = defaultText
        } else{
            val text = hashtags.joinToString(" ") { "#$it" }
            this.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("bookmark")
    fun TextView.setBookmark(bookmarks: List<Int>?) {
        val text = "${bookmarks?.size ?: 0}개의 좋아요"
        this.text = text
    }

    @JvmStatic
    @BindingAdapter("spinner_school")
    fun Spinner.setSpinnerSchool(schoolName : String){

    }
}
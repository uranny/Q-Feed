package com.example.cardsnap.ui.adapter.item

import java.time.LocalDateTime

data class Cmt (
    val userImg : String,
    val userName : String,
    val userAffil : String,
    val cmtTxt : String,
    val time : LocalDateTime
)
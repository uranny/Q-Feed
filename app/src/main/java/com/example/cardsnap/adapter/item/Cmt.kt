package com.example.cardsnap.adapter.item

import java.time.LocalDateTime

data class Cmt (
    val userImg : String,
    val userName : String,
    val userAffil : String,
    val cmtTxt : String,
    val time : LocalDateTime
)
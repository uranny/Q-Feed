package com.example.cardsnap.domain.entity.item

import java.time.LocalDateTime

data class Cmt (
    val userImg : String,
    val userName : String,
    val userAffil : String,
    val cmtTxt : String,
    val time : LocalDateTime
)
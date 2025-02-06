package com.example.cardsnap.domain.mapper

import com.example.cardsnap.domain.entity.item.Post
import com.example.cardsnap.domain.entity.response.GetUserInfoResponse

fun GetUserInfoResponse.toPost() : Post {
    return Post(
        this.id,
        this.uid,
        this.username,
        this.affiliation,
        this.grade,
        this.imageUrl,
        this.statusMessage,
        this.hashtags,
        this.age,
        this.height,
        this.weight,
        this.habbies,
        this.likes,
        this.dislikes,
        this.idealType,
        arrayListOf(),
        arrayListOf()
    )
}
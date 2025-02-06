package com.example.cardsnap.domain.mapper

import com.example.cardsnap.data.source.user.User
import com.example.cardsnap.domain.entity.item.Post
import com.example.cardsnap.domain.entity.response.MyPageResponse

fun MyPageResponse.toUser() : User {
    return User(
        id,
        uid,
        username,
        affiliation,
        grade,
        imageUrl,
        statusMessage,
        hashtags,
        age,
        height,
        weight,
        habbies,
        likes,
        dislikes,
        idealType
    )
}

fun Post.toUser() : User {
    return User(
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
        this.idealType
    )
}
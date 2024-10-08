package com.example.cardsnap.serverDaechae

import com.example.cardsnap.R

object User {

    // 다른 유저 프로필 들어갈 때 필요한 인덱스
    var profileIndex = 0

    // 로그인시 가지는 아이디 인덱스 값
    var logInIndex = 0

    // Chat에 접근 시에 필요한 Index
    var chatIndex : Int = -1

    // 로그인 회원가입 관련 리스트
    val emailLst = arrayListOf<String>()
    val idLst = arrayListOf<String>()
    val passLst = arrayListOf<String>()

    //recycleView 관련 리스트
    val chatLst = arrayListOf<ArrayList<Chat>>()
    val postLst : ArrayList<Post> = arrayListOf()
}
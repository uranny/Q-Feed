package com.example.cardsnap.serverDaechae

import com.example.cardsnap.R

object User {
    // 다른 유저 프로필 들어갈 때 필요한 인덱스
    var userProfileIndex = 0

    // 로그인시 가지는 아이디 인덱스 값
    var userLogInIndex = 0

    // Chat에 접근 시에 필요한 Index
    var userChatIndex : Int = -1

    // 로그인 회원가입 관련 리스트
    val userEmailLst = arrayListOf<String>()
    val userIdLst = arrayListOf<String>()
    val userPassLst = arrayListOf<String>()

    //recycleView 관련 리스트
    val userChatLst = arrayListOf<ArrayList<Chat>>()
    val postLst : ArrayList<Post> = arrayListOf()

    // 편집 화면 임시저장 변수들
    var inputEmail : String = ""
    var inputId : String = ""
    var inputPass : String = ""
    var editName : String = ""
    var editAffil : String = ""
    var editMessage : String = ""
    var editTag : String = ""
    var editAge : String = ""
    var editHeight : String = ""
    var editWeight : String = ""
    var editHabby : String = ""
    var editLikeThing : String = ""
    var editBadThing : String = ""
    var editIdeal : String = ""

}
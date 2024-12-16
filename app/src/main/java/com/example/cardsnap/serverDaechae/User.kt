package com.example.cardsnap.serverDaechae

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.cardsnap.R
import java.time.LocalDateTime

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
    val chatLst = arrayListOf<ArrayList<Chat>>() // 채팅 '방'
    val postLst : ArrayList<Post> = arrayListOf()
    @RequiresApi(Build.VERSION_CODES.O)
    val inChatLst = arrayListOf<InChat>(
        InChat("uranny", "안녕 내 친구", LocalDateTime.of(2024, 11, 28, 10, 10, 10)),
        InChat("uranny", "내 이름은 오정민이야 반가우이!", LocalDateTime.of(2024, 11, 28, 10, 10, 10))

    ) // '채팅'
    @RequiresApi(Build.VERSION_CODES.O)
    val cmtLst = arrayListOf<Cmt>(
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "1년전",
            LocalDateTime.of(2023, 8, 10, 10, 10)
        ),
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "3달 전",
            LocalDateTime.of(2024, 9, 1, 10, 10)
        ),
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "5일 전",
            LocalDateTime.of(2024, 11, 24, 10, 10)
        ),
        Cmt(
            "user",
            "몰라요",
            "구지중",
            "1시간 전",
            LocalDateTime.of(2024, 11, 29, 13, 20)
        ),Cmt(
            "user",
            "몰라요",
            "구지중",
            "몇 분 전",
            LocalDateTime.of(2024, 11, 28, 14, 20)
        )
    )
}
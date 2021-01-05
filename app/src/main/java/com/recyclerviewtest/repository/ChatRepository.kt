package com.recyclerviewtest.repository

class ChatRepository {
    val mutableList = mutableListOf<Int>()
    init {
        for (i in 0..99){
            mutableList.add(i)
        }
    }
}


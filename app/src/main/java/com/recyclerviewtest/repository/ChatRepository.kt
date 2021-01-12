package com.recyclerviewtest.repository

import java.util.*

class ChatRepository {
    val mutableList = mutableListOf<Int>()

    init {
        for (i in 0..14) {
            mutableList.add(i)
        }
    }

    val queue: Queue<Int> = LinkedList()
}


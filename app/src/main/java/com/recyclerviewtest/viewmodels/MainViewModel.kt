package com.recyclerviewtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.recyclerviewtest.extension.mutableLiveData
import com.recyclerviewtest.repository.ChatRepository

class MainViewModel : ViewModel() {
    fun getItems() : LiveData<List<Int>>{
        return mutableLiveData()
    }

    private val chatRepository : ChatRepository = ChatRepository()

}
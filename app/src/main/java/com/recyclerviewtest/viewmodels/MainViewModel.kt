package com.recyclerviewtest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recyclerviewtest.extension.mutableLiveData
import com.recyclerviewtest.repository.ChatRepository
import java.util.concurrent.Executors
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val liveDataItems: MutableLiveData<List<Int>> = mutableLiveData()
    private val liveDataRemoveItem: MutableLiveData<Int> = mutableLiveData()
    private val liveDataInsertItem: MutableLiveData<Int> = mutableLiveData()
    private val lockObject = Object()
    private val chatRepository: ChatRepository = ChatRepository()
    private val executorService = Executors.newCachedThreadPool()
    private val generateItemRunnable = Runnable {
        synchronized(lockObject) {
            liveDataItems.postValue(chatRepository.mutableList)
        }
        while (true) {
            Thread.sleep(5000)
            synchronized(lockObject) {
                val sizeItems = chatRepository.mutableList.size
                val randomPosition =
                    if (sizeItems <= 1) 0 else Random.nextInt(sizeItems - 1)
                val item =
                    chatRepository.queue.poll() ?: ((chatRepository.mutableList.maxOrNull()
                        ?: 0) + 1)
                chatRepository.mutableList.add(randomPosition, item)
                liveDataItems.postValue(chatRepository.mutableList)
                liveDataInsertItem.postValue(randomPosition)
            }
        }
    }

    fun getItems(): LiveData<List<Int>> = liveDataItems
    fun getRemoveItem(): LiveData<Int> = liveDataRemoveItem
    fun getInsertItem(): LiveData<Int> = liveDataInsertItem

    fun loadItems() {
        generateItems()
    }

    private fun generateItems() {
        executorService.execute(generateItemRunnable)
    }

    fun deleteItem(position: Int) {
        if (position < 0) return
        executorService.execute {
            synchronized(lockObject) {
                chatRepository.queue.add(chatRepository.mutableList.removeAt(position))
                liveDataItems.postValue(chatRepository.mutableList)
                liveDataRemoveItem.postValue(position)
            }
        }
    }

}
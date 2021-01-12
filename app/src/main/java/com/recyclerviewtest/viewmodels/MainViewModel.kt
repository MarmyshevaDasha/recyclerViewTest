package com.recyclerviewtest.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recyclerviewtest.repository.ChatRepository
import java.util.concurrent.Executors
import java.util.concurrent.Future
import kotlin.random.Random

class MainViewModel : ViewModel() {
    private val liveDataItems: MutableLiveData<List<Int>> = MutableLiveData()
    private val liveDataRemoveItem: MutableLiveData<Int> = MutableLiveData()
    private val liveDataInsertItem: MutableLiveData<Int> = MutableLiveData()
    private val chatRepository: ChatRepository = ChatRepository()
    var isPressedButton: Boolean = false
    private val executorService = Executors.newCachedThreadPool()
    private val generateItemRunnable = Runnable {
        liveDataItems.postValue(chatRepository.mutableList)
        while (true) {
            Thread.sleep(5000)
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

    private var futureGenerateItems: Future<*>? = null

    fun getItems(): LiveData<List<Int>> = liveDataItems
    fun getRemoveItem(): LiveData<Int> = liveDataRemoveItem
    fun getInsertItem(): LiveData<Int> = liveDataInsertItem

    fun loadItems() {
        generateItems()
    }

    private fun generateItems() {
        if (futureGenerateItems == null || futureGenerateItems?.isCancelled == true || futureGenerateItems?.isDone == true) {
            futureGenerateItems = executorService.submit(generateItemRunnable)
        }
    }

    fun deleteItem(position: Int) {
        if (isPressedButton) return
        isPressedButton = true
        if (position < 0) return
        executorService.submit {
            chatRepository.queue.add(chatRepository.mutableList.removeAt(position))
            liveDataItems.postValue(chatRepository.mutableList)
            liveDataRemoveItem.postValue(position)
        }
    }
}
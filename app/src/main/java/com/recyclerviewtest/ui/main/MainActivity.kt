package com.recyclerviewtest.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerviewtest.R
import com.recyclerviewtest.ui.adapters.ChatAdapter
import com.recyclerviewtest.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var chatAdapter: ChatAdapter
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.getItems().observe(this) { items -> chatAdapter.updateData(items) }
        viewModel.getRemoveItem().observe(this) { items -> chatAdapter.deleteItem(items) }
        viewModel.getInsertItem().observe(this) { items -> chatAdapter.insertItem(items) }
        chatAdapter = ChatAdapter()
        chatAdapter.clickListener = { item -> viewModel.deleteItem(item) }
        initViews()
        viewModel.loadItems()
    }

    private fun initViews() {
        val rvChatList: RecyclerView = findViewById(R.id.rv_chat_list)
        with(rvChatList) {
            adapter = chatAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }
}
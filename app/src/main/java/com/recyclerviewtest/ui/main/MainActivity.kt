package com.recyclerviewtest.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerviewtest.R
import com.recyclerviewtest.ui.adapters.ChatAdapter
import com.recyclerviewtest.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var chatAdapter: ChatAdapter
    val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.getItems().observe(this, Observer { chatAdapter.updateData(it.toMutableList()) })
        chatAdapter = ChatAdapter()
        initViews()

    }

    private fun initViews() {
        val rvChatList: RecyclerView = findViewById(R.id.rv_chat_list)
        with(rvChatList) {
            adapter = chatAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }
}
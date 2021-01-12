package com.recyclerviewtest.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recyclerviewtest.R
import com.recyclerviewtest.ui.adapters.ItemsAdapter
import com.recyclerviewtest.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var itemsAdapter: ItemsAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel.getItems().observe(this) { items -> itemsAdapter.updateData(items) }
        viewModel.getRemoveItem().observe(this) { items -> itemsAdapter.deleteItem(items) }
        viewModel.getInsertItem().observe(this) { items -> itemsAdapter.insertItem(items) }
        itemsAdapter = ItemsAdapter()
        itemsAdapter.clickListener = { item -> viewModel.deleteItem(item) }
        initViews()
        viewModel.loadItems()
    }

    private fun initViews() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = itemsAdapter
        val orientation = resources.configuration.orientation
        recyclerView.layoutManager = GridLayoutManager(
            this@MainActivity,
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
        )
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener {
            viewModel.isPressedButton = false
            viewModel.notifyItem()
        }
    }
}
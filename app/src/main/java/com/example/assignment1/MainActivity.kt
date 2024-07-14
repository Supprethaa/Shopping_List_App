package com.example.assignment1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var editTextItem: EditText
    private lateinit var buttonAdd: Button
    private lateinit var spinnerCategory: Spinner
    private lateinit var recyclerViewItems: RecyclerView
    private val itemList = mutableListOf<String>()
    private lateinit var itemAdapter: ItemAdapter
    private var selectedCategory: String = "Grocery"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextItem = findViewById(R.id.editTextItem)
        buttonAdd = findViewById(R.id.buttonAdd)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        recyclerViewItems = findViewById(R.id.recyclerViewItems)

        setupSpinner()
        setupRecyclerView()

        buttonAdd.setOnClickListener {
            addItem()
        }
    }

    private fun setupSpinner() {
        val categories = arrayOf("", "Grocery", "Electronics", "Clothing", "Other") // Added an empty string
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedCategory = categories[position]
                if (selectedCategory.isEmpty()) {
                    // No category selected, action can be handled here
                    Toast.makeText(this@MainActivity, "No category selected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Action when nothing is selected
                selectedCategory = "No category selected"
            }
        }
    }


    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(itemList)
        recyclerViewItems.layoutManager = LinearLayoutManager(this)
        recyclerViewItems.adapter = itemAdapter
    }

    private fun addItem() {
        val item = editTextItem.text.toString()
        if (item.isNotEmpty()) {
            itemList.add("$item - $selectedCategory")
            itemAdapter.notifyDataSetChanged()
            editTextItem.text.clear()
        } else {
            Toast.makeText(this, "Please enter an item name", Toast.LENGTH_SHORT).show()
        }
    }
}

package com.example.assignment1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextItem: EditText
    private lateinit var editTextQuantity: EditText
    private lateinit var buttonAdd: Button
    private lateinit var spinnerCategory: Spinner
    private lateinit var recyclerViewItems: RecyclerView
    private val itemList = mutableListOf<ShoppingItem>() // Change to use ShoppingItem
    private lateinit var itemAdapter: ItemAdapter
    private var selectedCategory: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextItem = findViewById(R.id.editTextItem)
        editTextQuantity = findViewById(R.id.editTextQuantity)
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
        val categories = arrayOf("", "Grocery", "Electronics", "Clothing", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter

        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedCategory = categories[position]
                if (selectedCategory.isEmpty()) {
                    Toast.makeText(this@MainActivity, "No category selected", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCategory = "No category selected"
            }
        }
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(itemList) { position, isChecked ->
            // Handle checkbox state change
            itemList[position].isPurchased = isChecked
        }
        recyclerViewItems.layoutManager = LinearLayoutManager(this)
        recyclerViewItems.adapter = itemAdapter
    }

    private fun addItem() {
        val item = editTextItem.text.toString()
        val quantity = editTextQuantity.text.toString().toIntOrNull()

        if (item.isNotEmpty() && quantity != null) {
            val newItem = ShoppingItem("$item - $selectedCategory", quantity, false)
            itemList.add(newItem)
            itemAdapter.notifyItemInserted(itemList.size - 1)
            editTextItem.text.clear()
            editTextQuantity.text.clear()
        } else {
            Toast.makeText(this, "Please enter valid item name and quantity", Toast.LENGTH_SHORT).show()
        }
    }
}

// Add this data class at the end of the file or in a separate file
data class ShoppingItem(
    val name: String,
    val quantity: Int,
    var isPurchased: Boolean = false
)
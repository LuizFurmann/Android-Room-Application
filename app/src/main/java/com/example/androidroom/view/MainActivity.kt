package com.example.androidroom.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidroom.databinding.ActivityMainBinding
import com.example.androidroom.model.Contact
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val contactViewModel: ContactViewModel by viewModels()
    private val contactAdapter = ContactAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
    }

    private fun setupViewModel() {
        contactViewModel.readAllData().observe(this) { contacts ->
            updateList(contacts)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvContact.layoutManager = layoutManager
        binding.rvContact.adapter = contactAdapter
        binding.rvContact.setHasFixedSize(true)
    }

    private fun updateList(contacts: List<Contact>) {
        if (contacts.isEmpty()) {
            binding.rvContact.visibility = View.GONE
        } else {
            binding.rvContact.visibility = View.VISIBLE
            contactAdapter.updateList(contacts)
        }
    }
}
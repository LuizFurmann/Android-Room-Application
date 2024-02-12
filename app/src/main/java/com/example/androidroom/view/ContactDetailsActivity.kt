package com.example.androidroom.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.androidroom.R
import com.example.androidroom.databinding.ActivityContactDetailsBinding
import com.example.androidroom.model.Contact

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityContactDetailsBinding
    private lateinit var contact : Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityContactDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        if (intent.getSerializableExtra("Contact") != null) {
            contact = intent.getSerializableExtra("Contact") as Contact
            updateView(contact)
        } else {
            binding.etName.isEnabled = true
            binding.etPhone.isEnabled = true
        }
    }

    private fun updateView(contact: Contact) {
        binding.etName.setText(contact.name)
        binding.etPhone.setText(contact.phone)
    }
}
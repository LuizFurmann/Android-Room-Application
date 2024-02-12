package com.example.androidroom.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.androidroom.R
import com.example.androidroom.databinding.ActivityContactDetailsBinding
import com.example.androidroom.model.Contact
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailsBinding
    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityContactDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar()
        setupView()
        saveTask()
    }

    private fun setupView() {
        if (intent.getSerializableExtra("Contact") != null) {
            contact = intent.getSerializableExtra("Contact") as Contact

            binding.btnSaveContact.visibility = View.GONE
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveTask() {
        binding.btnSaveContact.setOnClickListener {
            val context = this
            val colorState = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.red)
                )
            )

            val colorStateValid = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_enabled)),
                intArrayOf(
                    ContextCompat.getColor(context, R.color.grey)
                )
            )

            if (binding.tilName.editText?.text.toString().isNullOrEmpty()) {
                binding.tilName.editText?.error = "Campo obrigatório"
                binding.tilName.setBoxStrokeColorStateList(colorState)
                binding.tilName.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.tilPhone.editText?.error = null
                binding.tilPhone.setBoxStrokeColorStateList(colorStateValid)
                binding.tilPhone.hintTextColor =
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.grey))
            }

            if (validateExercise()) {
                if (intent.getSerializableExtra("Contact") != null) {
                    updateTask()
                    finish()
                } else {
                    insertDataToDatabase()
                    finish()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validateExercise(): Boolean {
        if (binding.tilName.editText?.text.toString().isEmpty()) {
            return false
        }
        if (binding.tilPhone.editText?.text.toString().isEmpty()) {
            return false
        }

        return true
    }

    private fun insertDataToDatabase() {
        val task = Contact(
            0,
            binding.etName.text.toString(),
            binding.etPhone.text.toString(),
        )
        contactViewModel.addTask(task)
        Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_LONG).show()
    }

    private fun updateTask() {
        contact = intent.getSerializableExtra("Contact") as Contact

        var id = contact.contactId
        val updatedContact = Contact(
            id,
            binding.etName.text.toString(),
            binding.etPhone.text.toString()
        )
        contactViewModel.updateTask(updatedContact)
    }

    private fun deleteTaskConfirmation() {
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage("Deseja deletar o contato?")
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            deleteContact()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }
    private fun deleteContact() {
        contact = intent.getSerializableExtra("Contact") as Contact
        var id = contact.contactId

        val contactForDelete =
            Contact(
                id,
                contact.name,
                contact.phone
            )
        contactViewModel.deleteTask(contactForDelete)
        Toast.makeText(this, "Deletado com sucesso!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun setupToolbar() {
        title = ""
        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (intent.getSerializableExtra("Contact") != null) {
            menuInflater.inflate(R.menu.menu_item, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        if (item.itemId == R.id.edit) {
            binding.etName.isEnabled = true
            binding.etPhone.isEnabled = true
            binding.btnSaveContact.visibility = View.VISIBLE

            return true
        } else if (item.itemId == R.id.delete) {
            deleteTaskConfirmation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
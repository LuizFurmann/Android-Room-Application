package com.example.androidroom.view

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidroom.R
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
        title = "Contatos"

        setupViewModel()
        setupRecyclerView()
        clickListener()
    }

    private fun clickListener(){
        binding.fabNewContact.setOnClickListener {
            Intent(this@MainActivity, ContactDetailsActivity::class.java).also{
                startActivity(it)
            }
        }
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
            binding.tvTitle.visibility = View.GONE
            binding.imgEmpty.visibility = View.VISIBLE
        } else {
            binding.rvContact.visibility = View.VISIBLE
            binding.tvTitle.visibility = View.VISIBLE
            binding.imgEmpty.visibility = View.GONE
            contactAdapter.updateList(contacts)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)

        val textChangeListener: SearchView.OnQueryTextListener =
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(query: String): Boolean {
                    contactAdapter.getFilter().filter(query)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    contactAdapter.getFilter().filter(query)
                    return true
                }
            }
        searchView.setOnQueryTextListener(textChangeListener)
        return true
    }
}
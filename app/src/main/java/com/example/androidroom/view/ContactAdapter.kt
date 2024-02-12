package com.example.androidroom.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidroom.StringHelper
import com.example.androidroom.databinding.RowContactBinding
import com.example.androidroom.model.Contact
import java.util.Locale

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.TaskViewHolder>() {

    private var contactList = arrayListOf<Contact>()
    private var contactFiltered = arrayListOf<Contact>()
    var noteStatus = false
    lateinit var context: Context

    fun updateList(tasks: List<Contact>) {
        this.contactList.clear()
        this.contactList.addAll(tasks)
        this.contactFiltered.clear()
        this.contactFiltered.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactAdapter.TaskViewHolder {
        val itemBinding = RowContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ContactAdapter.TaskViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.name.text = currentItem.name

        holder.itemView.setOnClickListener {

            Intent(holder.itemView.context, ContactDetailsActivity::class.java).also{
                it.putExtra("Contact", currentItem)
                holder.itemView.context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class TaskViewHolder(private val itemBinding: RowContactBinding) : RecyclerView.ViewHolder(itemBinding.root){
        var name = itemBinding.tvName
    }

    fun getFilter(): Filter {
        val filter : Filter

        filter = object : Filter(){
            override fun performFiltering(filter: CharSequence): FilterResults {
                var filter = filter
                val results = FilterResults()

                if (filter.isEmpty()) {
                    results.count = contactFiltered.size
                    results.values = contactFiltered
                } else {
                    val filteredItems: MutableList<Contact> = ArrayList<Contact>()
                    for (i in contactFiltered.indices) {
                        filter = filter.toString().toLowerCase(Locale.ROOT)
                        val data = contactFiltered[i]

                        val name = StringHelper.getFilterText(data.name)

                        if(name.contains(filter)) {
                            filteredItems.add(data)
                        }

                    }
                    results.count = filteredItems.size
                    results.values = filteredItems
                }
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                contactList = results.values as ArrayList<Contact>
                notifyDataSetChanged()
            }

        }
        return filter
    }
}
package edu.ufp.daodatabase.fragments.list

import android.os.Bundle
import android.text.Selection.setSelection
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import edu.ufp.daodatabase.R
import edu.ufp.daodatabase.data.model.Invoice
import kotlinx.android.synthetic.main.custom_row.view.*
import androidx.navigation.findNavController
import edu.ufp.daodatabase.fragments.list.ListFragmentDirections
class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var invoiceList = emptyList<Invoice>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent,false))
    }

    override fun getItemCount(): Int {
        return invoiceList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = invoiceList[position]
        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.filename_txt.text = currentItem.filename

        holder.itemView.rowLayout.setOnClickListener {
            // Change fragment with data
            var action = ListFragmentDirections.actionListFragmentToViewFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(invoice: List<Invoice>){
        this.invoiceList = invoice
        notifyDataSetChanged()
    }
}
package edu.ufp.daodatabase.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.ufp.daodatabase.R
import edu.ufp.daodatabase.data.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private lateinit var mInvoiceViewModel: InvoiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Get viewModel
        mInvoiceViewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)
        mInvoiceViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            invoice -> adapter.setData(invoice)
        })

        // Floating Button
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }
}
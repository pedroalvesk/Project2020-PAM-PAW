package edu.ufp.daodatabase.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.ufp.daodatabase.R
import edu.ufp.daodatabase.data.model.Invoice
import edu.ufp.daodatabase.data.viewmodel.InvoiceViewModel
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var mInvoiceViewModel: InvoiceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        // Get viewModel
        mInvoiceViewModel = ViewModelProvider(this).get(InvoiceViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val filename = "FilenameTeste"
        val processed = false
        val data = "TesteData"
        val type = "Teste TYPE"

        // Create object
        val invoice = Invoice(0, filename, processed, data, type)

        // Add data to database
        mInvoiceViewModel.addInvoice(invoice)
        Toast.makeText(requireContext(), "Success added!", Toast.LENGTH_LONG).show()

        // Navigate Back
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }
}
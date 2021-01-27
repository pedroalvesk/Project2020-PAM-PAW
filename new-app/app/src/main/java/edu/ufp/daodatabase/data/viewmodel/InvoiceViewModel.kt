package edu.ufp.daodatabase.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.ufp.daodatabase.data.InvoiceDatabase
import edu.ufp.daodatabase.data.model.Invoice
import edu.ufp.daodatabase.data.repository.InvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 *  Provide data to the UI. Acts as a communication center between Repository and UI.
 */
class InvoiceViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Invoice>>
    private val repository: InvoiceRepository

    init {
        val invoiceDao = InvoiceDatabase.getDatabase(application).invoiceDao()
        repository = InvoiceRepository(invoiceDao)
        readAllData = repository.readAllData
    }


    fun addInvoice(invoice: Invoice){
        // Run in background thread
        viewModelScope.launch(Dispatchers.IO) {
            repository.addInvoice(invoice)
        }
    }
}
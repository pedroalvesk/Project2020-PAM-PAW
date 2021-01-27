package edu.ufp.daodatabase.data.repository

import androidx.lifecycle.LiveData
import edu.ufp.daodatabase.data.InvoiceDao
import edu.ufp.daodatabase.data.model.Invoice

/*
 * Abstracts access to multiple data sources // Best practice suggested
 */
class InvoiceRepository(private val invoiceDao: InvoiceDao) {

    val readAllData: LiveData<List<Invoice>> = invoiceDao.readAllData()

    suspend fun addInvoice(invoice: Invoice){
        invoiceDao.addInvoice(invoice)
    }
}
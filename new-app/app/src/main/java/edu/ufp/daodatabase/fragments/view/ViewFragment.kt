package edu.ufp.daodatabase.fragments.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import edu.ufp.daodatabase.R
import edu.ufp.daodatabase.data.model.Invoice
import edu.ufp.daodatabase.fragments.view.ViewFragmentArgs

class ViewFragment : Fragment() {

    private val args by navArgs<ViewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_view, container, false)

        Log.e("ARGS", args.currentItem.toString());


        return view
    }

}
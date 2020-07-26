package dev.ghouse.ghousedevdemoapp.ui.viewsaveddata

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ghouse.ghousedevdemoapp.R
import dev.ghouse.ghousedevdemoapp.ui.adapters.SavedFormsAdapter
import kotlinx.android.synthetic.main.view_saved_fragment.*
import kotlinx.coroutines.launch

class ViewSavedFragment : Fragment(R.layout.view_saved_fragment) {

    private val viewModel: ViewSavedViewModel by activityViewModels()

    init {
        lifecycleScope.launch {
            whenCreated {
            }
            whenStarted {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            setupUi()
        }
    }

    private fun setupUi() {
        // Get the recycler view in the fragment layout
        val recyclerView = view_saved_recycler_view
        // Get the custom adapter
        val adapter = SavedFormsAdapter(requireContext())
        // Apply the adapter to the view
        recyclerView.adapter = adapter
        // Set the layout manager for the view
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Observe the data from the view model
        viewModel.getLiveData()?.observe(viewLifecycleOwner, Observer {
            adapter.setForms(it)
        })
        // Set the data to display
        adapter.setForms(viewModel.getData()!!)
    }
}
package dev.ghouse.ghousedevdemoapp.ui.formdemo

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import dev.ghouse.ghousedevdemoapp.R
import kotlinx.android.synthetic.main.fragment_formdemo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormDemoFragment : Fragment(R.layout.fragment_formdemo) {

    private val viewModel: FormDemoViewModel by activityViewModels()

    init {
        lifecycleScope.launch {
            whenStarted {
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            // Set up an array adapter and bind it to the dropdown in the UI
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                viewModel.dropdownList(requireContext())
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter.filter.filter("")
            dropdown_options.setAdapter(adapter)
            // onClickListeners for buttons
            buttonListeners()
            setupUi()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        lifecycleScope.launch(Dispatchers.Main) {
            // Set up an array adapter and bind it to the dropdown in the UI, repeated here so the list stays applied to the dropdown
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                viewModel.dropdownList(requireContext())
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            adapter.filter.filter("")
            dropdown_options.setAdapter(adapter)
        }
    }

    private fun setupUi() {
        // Observer for textView containing the title
        viewModel.text.observe(viewLifecycleOwner, Observer {
            text_formdemo.text = it
        })
        // Observers that hold the values of the input fields to re-populate on config changes and listeners to update view model on input
        viewModel.getTextField(1).observe(viewLifecycleOwner, Observer {
            form_field_one.text = SpannableStringBuilder(it)
        })
        form_field_one.addTextChangedListener() { editable ->
            viewModel.setTextField(editable.toString(), 1)
        }
        viewModel.getTextField(2).observe(viewLifecycleOwner, Observer {
            form_field_two.text = SpannableStringBuilder(it)
        })
        form_field_two.addTextChangedListener() {
            viewModel.setTextField(it.toString(), 2)
        }
        viewModel.getTextField(3).observe(viewLifecycleOwner, Observer {
            form_field_three.text = SpannableStringBuilder(it)
        })
        form_field_three.addTextChangedListener() {
            viewModel.setTextField(it.toString(), 3)
        }
        viewModel.getTextField(4).observe(viewLifecycleOwner, Observer {
            form_field_four.text = SpannableStringBuilder(it)
        })
        form_field_four.addTextChangedListener() {
            viewModel.setTextField(it.toString(), 4)
        }
        viewModel.getTextField(5).observe(viewLifecycleOwner, Observer {
            form_field_five.text = SpannableStringBuilder(it)
        })
        form_field_five.addTextChangedListener() {
            viewModel.setTextField(it.toString(), 5)
        }
        // Checkbox observer
        viewModel.checkBox.observe(viewLifecycleOwner, Observer {
            form_checkedTextView.isChecked = it
        })
        form_checkedTextView.setOnCheckedChangeListener { _, b ->
            viewModel.setCheckBoxState(b)
        }
        // Switch state observer
        viewModel.toggleSwitch.observe(viewLifecycleOwner, Observer {
            form_switch.isChecked = it
        })
        form_switch.setOnCheckedChangeListener { _, b ->
            viewModel.setSwitchState(b)
        }
        // Observer that sets the initial text of the dropdown to selection on restore state
        viewModel.getDropdownSelectedItem().observe(viewLifecycleOwner, Observer {
            dropdown_options.setText(SpannableStringBuilder(it), false)
        })
        // Listener to save the selected item of the dropdown to the view model
        dropdown_options.setOnItemClickListener { _, _, i, _ ->
            viewModel.setDropdownSelectedItem(dropdown_options.adapter.getItem(i).toString())
        }
    }

    private fun buttonListeners() {
        val progressBar = requireView().rootView.findViewById<ProgressBar>(R.id.progress_bar)
        // Opens save dialog
        form_save_button.setOnClickListener {
            // Show progress bar
            progressBar.visibility = View.VISIBLE
            // Use the inputs saved in the view model to create a document
            viewModel.documentWork(requireContext())
            // Insert the values into the apps database table as a new column
            viewModel.insert(viewModel.getModel()!!)
            // Hide the progress bar
            progressBar.visibility = View.GONE
            // Use navigation architecture component to open a dialog
            findNavController().navigate(R.id.action_nav_formdemo_to_nav_save_document_dialog)
        }
    }
}

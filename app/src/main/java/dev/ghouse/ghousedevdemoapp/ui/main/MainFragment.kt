package dev.ghouse.ghousedevdemoapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.Navigation
import dev.ghouse.ghousedevdemoapp.R
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val mainViewModel: MainViewModel by activityViewModels()

    init {
        lifecycleScope.launch {
            whenStarted {
                mainViewModel.text.observe(viewLifecycleOwner, Observer {
                    text_gallery.text = it
                })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Send user to the form demo fragment using navigation architecture component
        form_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_main_to_nav_formdemo))
    }
}
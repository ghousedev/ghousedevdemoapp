package dev.ghouse.ghousedevdemoapp.ui.login

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.Navigation
import dev.ghouse.ghousedevdemoapp.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by activityViewModels()

    init {
        // TODO Use firebase auth for login or implement other login logic
        lifecycleScope.launch {
            whenStarted {
                // Hide the progress bar
                val progressBar =
                    requireView().rootView.findViewById<ProgressBar>(R.id.progress_bar)
                progressBar.visibility = View.GONE
                // Observe username field
                viewModel.getUsername().observe(viewLifecycleOwner, Observer {
                    usernameInput.text = it
                })
                // Send any input to the view model
                usernameInput.addTextChangedListener() {
                    viewModel.setUsername(it!!)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Sends user to the main screen
        login_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_nav_login_to_nav_main))
    }
}
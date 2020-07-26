package dev.ghouse.ghousedevdemoapp.ui.preview

import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import dev.ghouse.ghousedevdemoapp.R
import dev.ghouse.ghousedevdemoapp.ui.formdemo.FormDemoViewModel
import kotlinx.android.synthetic.main.fragment_pdf_preview.*
import kotlinx.coroutines.launch

// Fragment for displaying a pdf file
class PreviewPdfFragment : Fragment(R.layout.fragment_pdf_preview) {
    // Shares FormDemoViewModel with FormDemo fragment
    private val viewModel: FormDemoViewModel by activityViewModels()

    init {
        lifecycleScope.launch {
            whenStarted {
                // Get the progress bar
                val progressBar =
                    requireView().rootView.findViewById<ProgressBar>(R.id.progress_bar)
                // Show the progress bar
                progressBar.visibility = View.VISIBLE
                // Get the pdf to display from the view model
                pdfView.fromFile(viewModel.getFileForPreview()).load()
                // Hide the progress bar
                progressBar.visibility = View.GONE
            }
        }
    }
}
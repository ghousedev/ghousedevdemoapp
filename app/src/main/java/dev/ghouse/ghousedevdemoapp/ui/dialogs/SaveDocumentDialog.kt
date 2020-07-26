package dev.ghouse.ghousedevdemoapp.ui.dialogs

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.ghouse.ghousedevdemoapp.R
import dev.ghouse.ghousedevdemoapp.ui.formdemo.FormDemoViewModel
import dev.ghouse.ghousedevdemoapp.utils.EmailWithIntent
import java.io.File

class SaveDocumentDialog : DialogFragment() {

    private val viewModel: FormDemoViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return MaterialAlertDialogBuilder(context)
            // Set the title
            .setTitle("Document")
            // Set the dialog message
            .setMessage("The document has been saved as a .pdf on your device.")
            // Positive button click listener
            .setPositiveButton("Preview") { dialogInterface, i ->
                findNavController().navigate(R.id.nav_preview_pdf)
            }
            // Negative button click listener
            .setNegativeButton("Send as email") { dialogInterface, i ->
                // Get the application context
                val appContext = requireContext().applicationContext
                // Get the directory the file is stored in
                val filePath = File(appContext.getExternalFilesDir(null), "Documents/")
                // Set the file by providing the filename as a child
                val attachmentFile = File(filePath, "form.pdf")
                // Empty Uri to hold Uri if the file can be read
                var attachmentUri: Uri = "".toUri()
                // Determine if the file can be read from
                if (attachmentFile.canRead()) {
                    attachmentUri =
                        getUriForFile(appContext, appContext.packageName, attachmentFile)
                } else {
                    Log.d("FILE", "File not found.")
                }
                // Call the function with its required params to send email
                EmailWithIntent.emailWithAttachment(
                    context = requireContext(),
                    receiverEmail = viewModel.getTextField(5).value.toString(),
                    subject = "", messageBody = "", attachmentUri = attachmentUri
                )
            }
            .setNeutralButton("Back") { dialogInterface, i ->
                // Dismiss the dialog
                dialogInterface.dismiss()
            }
            // Show the dialog
            .show()
    }
}
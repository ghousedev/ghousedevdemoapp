package dev.ghouse.ghousedevdemoapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class EmailWithIntent {
    companion object {
        // Gives user a choice of which email client to use to send mail composed of data from the app
        fun emailWithAttachment(
            context: Context,
            receiverEmail: String,
            subject: String,
            messageBody: String,
            attachmentUri: Uri
        ) {
            // New intent object
            val intent = Intent(Intent.ACTION_SEND)
            // MIME type
            intent.type = "text/plain"
            // Add array of recipients as strings
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(receiverEmail))
            // Set the subject
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            // Set the message body
            intent.putExtra(Intent.EXTRA_TEXT, messageBody)
            // Set the attachment file
            intent.putExtra(Intent.EXTRA_STREAM, attachmentUri)
            // Set the flags for the intent
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            // Use the intent to show a chooser dialog with options of apps to open the email in
            context.startActivity(Intent.createChooser(intent, "Choose a mail client"))
        }

        // Same as above but with no attachment
        fun emailWithoutAttachment(
            context: Context,
            receiverEmail: String,
            subject: String,
            messageBody: String
        ) {
            // New intent object
            val intent = Intent(Intent.ACTION_SEND)
            // MIME type
            intent.type = "text/plain"
            // Add array of recipients as strings
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(receiverEmail))
            // Set the subject
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            // Set the message body
            intent.putExtra(Intent.EXTRA_TEXT, messageBody)
            // Set the flags for the intent
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            // Use the intent to show a chooser dialog with options of apps to open the email in
            context.startActivity(Intent.createChooser(intent, "Choose a mail client"))
        }
    }
}
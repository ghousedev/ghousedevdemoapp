package dev.ghouse.ghousedevdemoapp.utils

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileFromResources {
    // Retrieve a file from the applications asset folder
    companion object {
        // Takes context, name of the file to retrieve and the name you want to save the file as and returns a new file with the name
        // 'savedFileName' or if it already exists it returns the existing file.
        fun getFile(context: Context, fileToRetrieveName: String, savedFileName: String): File {
            val pathToDir = context.applicationContext.externalMediaDirs.first().path
            val mediaDirContents = context.applicationContext.externalMediaDirs.first().listFiles()
            if (mediaDirContents!!.contains(File("$pathToDir/$fileToRetrieveName"))) {
                Log.d("File", "File already present on device.")
            } else {
                val inputStream = context.applicationContext.assets.open(fileToRetrieveName)
                val savedForm =
                    File(
                        context.applicationContext.externalMediaDirs.first(),
                        savedFileName
                    )
                val outputStream = FileOutputStream(savedForm)
                inputStream.use {
                    it.copyTo(outputStream)
                }
                // The below code can be removed if the above block works and closes stream correctly
                // inputStream.copyTo(outputStream)
                /*try {
                    inputStream.close()
                } catch (e: IOException) {
                    Log.d("IOException", e.toString())
                }*/
                try {
                    outputStream.flush()
                    outputStream.close()
                } catch (e: IOException) {
                    Log.d("IOException", e.toString())
                }
            }
            // Output the path of the file
            return File("$pathToDir/$savedFileName")
        }
    }
}
package dev.ghouse.ghousedevdemoapp.utils

import android.content.Context
import android.os.Environment
import io.github.lucasfsc.html2pdf.Html2Pdf
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File

class HtmlToPdf {

    companion object {
        fun addData(
            context: Context,
            templatePath: String,
            inputData: List<Any>,
            outputFileName: String
        ): File {
            // Get the template file and use JSoup to parse it into a document object
            val templateFile = File(templatePath)
            val template = Jsoup.parse(templateFile, "UTF-8")
            // Mutable list for elements found in the template
            val inputElements = mutableListOf<Element>()
            // Iterate through the elements of the template and add all of the 'span' elements to a list
            var elementIndex = 0
            template.select("span").forEach {
                inputElements.add(elementIndex, it)
                elementIndex++
            }
            // For every 'span' element in the list for this document, append the value to the element from inputDataList with the corresponding index
            template.run {
                var inputIndex = 0
                inputElements.forEach {
                    it.append(inputData.get(inputIndex).toString())
                    inputIndex++
                }
            }
            // Use the Html2Pdf library to convert the html object to a pdf document
            val file = File(
                context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                outputFileName
            )
            // If a file with the same name exists then delete it and create a new file
            if (file.exists()) {
                file.delete()
                file.createNewFile()
            }
            val converter = Html2Pdf.Companion.Builder()
                .context(context)
                // Use a string of the modified template
                .html(template.toString())
                // Create the file to hold the finished document
                .file(file)
                .build()
            // Complete the conversion
            converter.convertToPdf()
            // Outputs File object with new data appended
            return file
        }
    }
}
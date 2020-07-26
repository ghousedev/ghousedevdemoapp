package dev.ghouse.ghousedevdemoapp.ui.formdemo

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.ghouse.ghousedevdemoapp.R
import dev.ghouse.ghousedevdemoapp.data.FormDatabase
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormDemoData
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormEntity
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormRepository
import dev.ghouse.ghousedevdemoapp.utils.FileFromResources
import dev.ghouse.ghousedevdemoapp.utils.HtmlToPdf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File


class FormDemoViewModel(application: Application) : AndroidViewModel(application) {

    // Data class that holds values for and from the ui
    private var data: MutableLiveData<FormDemoData> = MutableLiveData()

    // Repository
    private var repository: FormRepository? = null

    // Variables to hold the template file and the output file
    private var templateFile: File? = null
    private var formFile: File? = null

    init {
        // Initialise the data class object
        data.value = FormDemoData()
        viewModelScope.launch(Dispatchers.Default) {
            // Initialise the repository
            val formDao = FormDatabase.getDatabase(application).formDao()
            repository = FormRepository(formDao)
        }
    }

    // Gets the model object with current values
    fun getModel(): FormDemoData? {
        return data.value
    }

    // Insert data into database
    fun insert(form: FormDemoData) = viewModelScope.launch(Dispatchers.IO) {
        val entity = FormEntity(
            form.id,
            form.dropdownSelected,
            form.inputOne,
            form.inputTwo,
            form.inputThree,
            form.inputFour,
            form.inputFive,
            form.checkBox,
            form.toggle
        )
        repository?.insertForm(entity)
    }

    // Get the array of strings for the dropdown
    fun dropdownList(context: Context): Array<String> {
        return context.resources.getStringArray(R.array.form_dropdown_options)
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is the form demo screen"
    }
    val text: LiveData<String> = _text

    // Get model data for text fields
    fun getTextField(fieldNumber: Int): LiveData<String> {
        var fieldData = ""
        when (fieldNumber) {
            1 -> fieldData = data.value!!.inputOne
            2 -> fieldData = data.value!!.inputTwo
            3 -> fieldData = data.value!!.inputThree
            4 -> fieldData = data.value!!.inputFour
            5 -> fieldData = data.value!!.inputFive
        }
        return MutableLiveData(fieldData)
    }

    // Set the model data for text fields to any ui input
    fun setTextField(input: String, fieldNumber: Int) {
        when (fieldNumber) {
            1 -> data.value!!.inputOne = input
            2 -> data.value!!.inputTwo = input
            3 -> data.value!!.inputThree = input
            4 -> data.value!!.inputFour = input
            5 -> data.value!!.inputFive = input
        }
    }

    // Checkbox
    private val _checkBox = MutableLiveData<Boolean>().apply {
        value = data.value!!.checkBox
    }
    var checkBox: LiveData<Boolean> = MutableLiveData(data.value!!.checkBox)
    fun setCheckBoxState(boolean: Boolean) {
        checkBox = MutableLiveData(boolean)
    }

    // Toggle switch
    private val _toggleSwitch = MutableLiveData<Boolean>().apply {
        value = data.value!!.toggle
    }
    var toggleSwitch: LiveData<Boolean> = _toggleSwitch
    fun setSwitchState(boolean: Boolean) {
        toggleSwitch = MutableLiveData(boolean)
    }

    // Get the selected item for dropdown
    fun getDropdownSelectedItem(): LiveData<String> {
        return MutableLiveData(data.value!!.dropdownSelected)
    }

    // Set the string to display as the selected item
    fun setDropdownSelectedItem(string: String) {
        data.value!!.dropdownSelected = string
    }

    // Produce a pdf document by inserting the data collected from the ui into a template
    fun documentWork(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        // Get the template file from the asset directory and save it to the device
        templateFile =
            FileFromResources.getFile(context, "form_template.html", "form_template.html")

        // Get the value from the dropdown as a string, if it is null then append a pre-defined string
        val dropdownEntry = if (data.value!!.dropdownSelected == "") {
            "No item selected"
        } else {
            getDropdownSelectedItem().toString()
        }

        // Pass the template location on the device and a list of values in the order they are to be appended
        formFile = HtmlToPdf.addData(
            context,
            templatePath = templateFile!!.path,
            inputData = listOf(
                getTextField(1).value.toString(),
                getTextField(2).value.toString(),
                getTextField(3).value.toString(),
                getTextField(4).value.toString(),
                getTextField(5).value.toString(),
                checkBox.value.toString(),
                toggleSwitch.value.toString(),
                dropdownEntry
            ),
            outputFileName = "form.pdf"
        )
    }

    fun getFileForPreview(): File? {
        return formFile
    }
}
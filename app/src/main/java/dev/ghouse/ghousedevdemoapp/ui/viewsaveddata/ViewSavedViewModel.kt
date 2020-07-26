package dev.ghouse.ghousedevdemoapp.ui.viewsaveddata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.ghouse.ghousedevdemoapp.data.FormDatabase
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormEntity
import dev.ghouse.ghousedevdemoapp.data.formdemo.FormRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewSavedViewModel(application: Application) : AndroidViewModel(application) {
    // Repository
    private lateinit var repository: FormRepository

    // Variable to hold form objects from the database
    private var allForms: LiveData<List<FormEntity>>? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Initialise the repository
            val formDao = FormDatabase.getDatabase(getApplication()).formDao()
            repository = FormRepository(formDao)
            allForms = repository.allForms()
        }
    }

    fun getLiveData(): LiveData<List<FormEntity>>? {
        viewModelScope.launch(Dispatchers.IO) {
            // Initialise the repository
            val formDao = FormDatabase.getDatabase(getApplication()).formDao()
            repository = FormRepository(formDao)
            allForms = repository.allForms()
        }
        return allForms
    }

    fun getData(): List<FormEntity>? {
        val data = MutableLiveData(allForms?.value)
        return data.value
    }
}
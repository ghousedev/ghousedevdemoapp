package dev.ghouse.ghousedevdemoapp.data.formdemo

import androidx.lifecycle.LiveData

// Repository class responsible for sending data to the database and fetching it
class FormRepository(private val formDao: FormDao) {
    // Get every form saved on the device as LiveData
    fun allForms(): LiveData<List<FormEntity>> {
        return formDao.getAll()
    }

    // Insert a form object into the database
    suspend fun insertForm(form: FormEntity) {
        formDao.insertEntry(form)
    }

    // Delete the whole Forms table
    suspend fun deleteTable() {
        formDao.deleteFormTable()
    }
}
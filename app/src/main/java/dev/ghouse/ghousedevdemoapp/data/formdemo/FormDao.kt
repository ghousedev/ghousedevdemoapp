package dev.ghouse.ghousedevdemoapp.data.formdemo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FormDao {
    // Get every form saved on the device as LiveData
    @Query("SELECT * FROM Forms")
    fun getAll(): LiveData<List<FormEntity>>

    // Insert a form object into the database
    @Insert
    suspend fun insertEntry(data: FormEntity)

    // Delete an entry in the database
    @Delete
    suspend fun delete(form: FormEntity)

    // Delete the whole Forms table
    @Query("DELETE FROM Forms")
    suspend fun deleteFormTable()
}
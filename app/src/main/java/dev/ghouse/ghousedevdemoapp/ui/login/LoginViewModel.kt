package dev.ghouse.ghousedevdemoapp.ui.login

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private var _username: LiveData<Editable> = MutableLiveData()
    fun getUsername(): LiveData<Editable> {
        return _username
    }

    fun setUsername(e: Editable) {
        _username = MutableLiveData(e)
    }
}
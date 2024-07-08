package com.lizw.core_apis.thirdpartlibs.datastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.lizw.core_apis.databinding.ActivityDatastoreDemoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatastoreDemoActivity : AppCompatActivity() {
    companion object {
        const val DATA_STORE_KEY_TEXT = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDatastoreDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSaveData.setOnClickListener {
            saveText(appDataStore, "Leo")
        }
        binding.btnGetData.setOnClickListener {
            getText(appDataStore)
        }
    }

    // store data
    fun saveText(dataStore: DataStore<Preferences>, content: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val textKey = stringPreferencesKey(DATA_STORE_KEY_TEXT)
            dataStore.edit { settings ->
                settings[textKey] = content
            }
        }
    }

    // read data
    fun getText(dataStore: DataStore<Preferences>) {
        lifecycleScope.launch(Dispatchers.IO) {
            val textKey = stringPreferencesKey(DATA_STORE_KEY_TEXT)
            dataStore.edit { settings ->
                val text = settings[textKey]
                println("name is $text")
            }
        }
    }
}
package com.lizw.core_apis.jetpack.datastore

import android.content.Context
import androidx.datastore.core.DataStore
// 是这个类，注意不是：import java.util.prefs.Preferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

// At the top level of your kotlin file:
// 命名：biz + DataStore
val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 *
 */
class DatastoreFactory {

}
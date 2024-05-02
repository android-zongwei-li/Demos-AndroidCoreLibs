package com.lizw.core_apis.thirdpartlibs.room

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

/**
 * Created by Li Zongwei on 2021/3/30.
 */
class RoomViewModel(private var applicationContext: Application) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RoomViewModel(this[APPLICATION_KEY] as Application)
            }
        }
    }

    private val personDataBase: PersonDataBase = PersonDataBase.getInstance(applicationContext)
    private val personDao = personDataBase.getPersonDao()

    fun insert(person: Person) = personDao.insertPerson(person)

    fun delete(person: Person) = personDao.deletePerson(person)

    fun deleteById(id: Int) = personDao.deletePerson(id)

    fun update(person: Person) = personDao.updatePerson(person)

    fun queryById(id: Int): Person? = personDao.queryPerson(id)

    fun query(): List<Person>? = personDao.queryPersons()
}
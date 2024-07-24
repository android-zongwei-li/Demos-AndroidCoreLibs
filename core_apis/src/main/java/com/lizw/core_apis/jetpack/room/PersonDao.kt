package com.lizw.core_apis.jetpack.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
 * Created by Li Zongwei on 2021/3/30.
 */
@Dao
interface PersonDao {
    companion object {
        const val tableName = "person"
    }

    /*
    * 如果插入一个已存在的唯一键记录，在这个例子中是 id 重复，则会报错。
    * 解决方法是指定重复插入时的策略。
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Delete
    fun deletePerson(person: Person)

    @Query("DELETE FROM $tableName WHERE id=:id")
    fun deletePerson(id: Int)

    @Update
    fun updatePerson(person: Person)

    @Query("SELECT * FROM $tableName")
    fun queryPersons(): List<Person>?

    @Query("SELECT * FROM $tableName  WHERE id = :id")
    fun queryPerson(id: Int): Person?
}

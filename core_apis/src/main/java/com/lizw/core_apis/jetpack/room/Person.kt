package com.lizw.core_apis.jetpack.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Li Zongwei on 2021/3/30.
 *
 * 一个 Person 实例，代表一行数据。
 */
@Entity(tableName = "person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    var mId: Int = 0,

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    var name: String,

    @ColumnInfo(name = "grade", typeAffinity = ColumnInfo.REAL)
    var grade: Double,

    @ColumnInfo(name = "isMan", typeAffinity = ColumnInfo.INTEGER)
    var isMan: Boolean,
) {
    @Ignore
    var extraInfo: String? = null

    override fun toString(): String {
        return "Person(mId=$mId, name=$name', grade=$grade, isMan=$isMan, extraInfo=$extraInfo)"
    }
}
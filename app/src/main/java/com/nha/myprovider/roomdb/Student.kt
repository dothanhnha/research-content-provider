package com.nha.myprovider.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name : String) {

}
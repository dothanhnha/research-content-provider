package com.nha.myprovider.roomdb

import android.database.Cursor
import androidx.room.*


@Dao
interface StudentDAO {
    @Query("SELECT * from student_table WHERE id = :id")
    fun getStudent(id: String): Student?

    @Insert
    fun insert(student: Student)

    @Update
    fun updateUsers(vararg users: Student)

    @Query("SELECT * FROM student_table")
    fun getAll(): Cursor

    @Query("DELETE FROM student_table")
    fun deleteAll()
}
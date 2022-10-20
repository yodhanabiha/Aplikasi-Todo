package com.example.tryapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDAO {
    @Update
    fun  update( taskModel: TaskModel)

    @Delete
    fun delete(taskModel: TaskModel)

    @Insert (onConflict = OnConflictStrategy.REPLACE)//untuk menghandel data yang sama
    fun insert(taskModel: TaskModel)

    @Query ("select * from tableTask WHERE completed =:completed")
    fun taskAll(completed: Boolean): LiveData<List<TaskModel>>

    @Query ("select * from tableTask WHERE completed =:completed AND date =:date")
    fun taskAll(completed: Boolean, date: Long): LiveData<List<TaskModel>>

    @Query ("DELETE from tableTask WHERE completed = 1")
    fun DeleteCompleted()

    @Query ("DELETE from tableTask")
    fun DeleteAll()

}
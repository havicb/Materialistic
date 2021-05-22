package com.example.hackernews.database.dao

import androidx.room.*

// define generic base Dao object for common db operations(CRUD)
@Dao
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(objects: List<T>)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}

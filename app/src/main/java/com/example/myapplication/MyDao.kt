package com.example.parser3

import androidx.room.*

@Dao
interface MyDao {
    @Query("SELECT * FROM items")
    fun getAll(): MutableList<Item>


    @Insert
    fun insert(item: Item)

    @Delete
    fun delete(item: Item)

    @Query("DELETE FROM items")
    fun tacticalNukeIncoming()

    @Update
    fun update(item: Item)
}
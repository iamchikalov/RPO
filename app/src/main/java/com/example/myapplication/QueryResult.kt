package com.example.parser3

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class QueryResult(val items: MutableList<Item>)

@Entity (tableName = "items")
data class Item(
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "name")      val name: String?,
    @ColumnInfo(name = "full_name") val full_name: String?,
    @ColumnInfo(name = "url")       val url: String?,
    @ColumnInfo(name = "description")val description: String?,
    @ColumnInfo(name = "language")  val language: String?,
    @Embedded (prefix = "owner_")
    val owner: Owner?
)

data class Owner(
    @PrimaryKey val id: Long?,
    @ColumnInfo(name = "login")     val login: String?,
    @ColumnInfo(name = "avatar_url")val avatar_url: String?
)
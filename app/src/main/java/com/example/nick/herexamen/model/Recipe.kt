package com.example.nick.herexamen.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(

    @PrimaryKey
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "products") val products: List<String>,
    @ColumnInfo(name = "allergies") val allergies: List<String>,
    @ColumnInfo(name = "kind") val kind: String
)
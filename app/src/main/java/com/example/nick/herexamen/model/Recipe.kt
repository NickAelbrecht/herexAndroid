package com.example.nick.herexamen.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "products") var products: List<String>,
    @ColumnInfo(name = "allergies") var allergies: List<String>,
    @ColumnInfo(name = "kind") var kind: String
) {}
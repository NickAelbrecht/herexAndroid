package com.example.nick.herexamen.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * De hoofdentiteit van de app. De velden worden via JSON voorzien, dit voor makkelijke communicatie met de REST API
 */
@Parcelize
@Entity(tableName = "recipe_table")
data class Recipe(

    @PrimaryKey
    @field:Json(name = "title") val title: String,
    @field:Json(name = "products") var products: List<String>,
    @field:Json(name = "allergies") var allergies: List<String>,
    @field:Json(name = "kind") val kind: String
): Parcelable
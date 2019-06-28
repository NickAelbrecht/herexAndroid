package com.example.nick.herexamen.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "answers") var answers: List<String>,
    @ColumnInfo(name = "correct_answer") var correctAnswer: String,
    @ColumnInfo(name = "subject") var subject: String
) {}
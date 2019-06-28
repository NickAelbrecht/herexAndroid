package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.nick.herexamen.model.Question

@Dao
interface QuestionDao {

    @Insert
    fun insert(question:Question)

    @Query("DELETE from question_table")
    fun deleteAll()

    //@Query("SELECT * from question_table ORDER BY question asc")
    //fun getAllQuestions() : LiveData<List<Question>>
}
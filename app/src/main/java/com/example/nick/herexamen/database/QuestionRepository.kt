package com.example.nick.herexamen.database

import android.arch.lifecycle.LiveData
import com.example.nick.herexamen.model.Question

class QuestionRepository(private val questionDao: QuestionDao) {
    val allQuestions:LiveData<List<Question>> = questionDao.getAllQuestions()
}
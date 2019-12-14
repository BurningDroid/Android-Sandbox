package com.youknow.codelabroom.data

import androidx.lifecycle.LiveData
import com.youknow.codelabroom.data.model.Word
import com.youknow.codelabroom.data.sources.WordDao

class WordRepository(private val wordDao: WordDao) {

    val allWords: LiveData<List<Word>> = wordDao.getAlphabetizedWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}
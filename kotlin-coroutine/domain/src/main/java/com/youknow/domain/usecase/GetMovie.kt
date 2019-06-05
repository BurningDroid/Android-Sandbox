package com.youknow.domain.usecase

import com.youknow.domain.model.Movie

interface GetMovie {

    suspend fun get(id: String): Movie

}
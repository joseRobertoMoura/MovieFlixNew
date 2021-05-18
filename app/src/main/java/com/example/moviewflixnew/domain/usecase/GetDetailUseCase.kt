package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.ui.model.DetailModel

interface GetDetailUseCase {

    suspend fun getDetail(id:String?): DetailModel
}
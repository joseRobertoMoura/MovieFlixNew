package com.example.moviewflixnew.domain.usecase

import com.example.moviewflixnew.domain.model.DetailUseCase

interface GetDetailUseCase {

   fun getDetail(
      id:String?,
      callbackSuccess:(success: DetailUseCase) -> Unit,
      callbackError: (error: String) -> Unit
   )
}
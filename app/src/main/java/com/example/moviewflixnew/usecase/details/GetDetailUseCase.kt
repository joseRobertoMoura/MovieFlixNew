package com.example.moviewflixnew.usecase.details

import com.example.moviewflixnew.data.model.detail.DetailResponse

interface GetDetailUseCase {

   suspend fun getDetail(
       id:String?,
       callbackSuccess:(success: DetailResponse?) -> Unit,
       callbackError: (error: String?) -> Unit
   )

}
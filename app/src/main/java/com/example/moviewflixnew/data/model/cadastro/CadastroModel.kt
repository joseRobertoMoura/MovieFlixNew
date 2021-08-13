package com.example.moviewflixnew.data.model.cadastro

data class CadastroModel(
    val email:String?,
    val password:String?,
    val name:String?
){
    constructor() : this(
        "",
        "",
        ""
    )
}
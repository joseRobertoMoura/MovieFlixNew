package com.example.moviewflixnew.repository.favorites

import android.content.Context
import com.example.moviewflixnew.data.model.MoviesModel
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class MovieFlixFavoritesRepositoryImpl @Inject constructor() : MovieFlixFavoritesRepository {

    override suspend fun favoritesDataBase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        movie: MoviesModel,
        context: Context
    ) {
        val setUserInfo = ManagmentPreferences(context)
        FirebaseDatabase.getInstance().getReference()
            .child("favorites")
            .child(setUserInfo.getInfoUserEmail().replace(".",""))
            .child(movie.id!!)
            .setValue(movie)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    callbackSucess.invoke("Sucesso Db")
                }else{
                    callbackError.invoke("Error Db")
                }
            }.addOnFailureListener {
                    callbackError.invoke("Error Db")
            }
    }

    override suspend fun getFavoritesDataBase(
        callbackSucess: (success: MutableList<MoviesModel?>) -> Unit,
        callbackError: (error: String) -> Unit,
        context: Context
    ) {
        val setUserInfo = ManagmentPreferences(context)
        FirebaseDatabase.getInstance().getReference("favorites")
            .child(setUserInfo.getInfoUserEmail().replace(".",""))
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot!!.children
                    val list:MutableList<MoviesModel?> = arrayListOf()
                    children.forEach{
                        val model = it.getValue(MoviesModel::class.java)
                        list.add(model)
                    }
                    callbackSucess.invoke(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    callbackError.invoke(error.toString())
                }

            })
    }

    override suspend fun deleteDataFromDataBase(
        callbackSucess: (success: String) -> Unit,
        callbackError: (error: String) -> Unit,
        movie: MoviesModel,
        context: Context
    ) {
        val setUserInfo = ManagmentPreferences(context)
        FirebaseDatabase.getInstance().getReference()
            .child("favorites")
            .child(setUserInfo.getInfoUserEmail().replace(".",""))
            .child(movie.id!!)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    callbackSucess.invoke("Remove sucess")
                }else{
                    callbackError.invoke("Error remove Db")
                }
            }.addOnFailureListener {
                callbackError.invoke("Error remove Db")
            }
    }

}
package com.example.moviewflixnew.ui.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.view.adapter.MovieFlixAdapter
import com.example.moviewflixnew.ui.view.dialog.DialogMessageErrorClass
import com.example.moviewflixnew.ui.view.fragments.utils.MessagesDialogUtils
import com.example.moviewflixnew.ui.viewModel.ListMoviesViewModel
import kotlinx.android.synthetic.main.fragment_list_movies.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("SameParameterValue")
class ListMoviesFragment(private var numPage: String) : Fragment(){

    lateinit var navController: NavController
    private val listMoviesViewModel: ListMoviesViewModel by viewModel()
    private var totalPages = 0
    private val messagesDialogUtils:MessagesDialogUtils by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val activity = activity as Context
        this.initViewModel(_numpage = numPage, view = view, activity = activity)
    }

    private fun initViewModel(_numpage:String, view: View, activity: Context) {
        listMoviesViewModel.init(_numpage)
        listMoviesViewModel.moviesList.observe(viewLifecycleOwner) {

            if (it.isNotEmpty()){
                setAdapter(view,activity,it)
            }else{
                Toast.makeText(
                    activity,
                    getString(R.string.Message_error_api),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        listMoviesViewModel.total.observe(viewLifecycleOwner) {
            totalPages = it.toInt()
        }
        listMoviesViewModel.errorApi.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                createDialog(activity, R.string.msg_internet_error)
            }else{
                //TODO
            }
        }
    }

    private fun createDialog(context: Context, codeMessage:Int) {
        val dialog = DialogMessageErrorClass.newInstance(messagesDialogUtils.getStringMsgError(context,codeMessage))
        dialog.show(parentFragmentManager,dialog.tag)
    }



    companion object {
        fun newInstance(numPage:String) = ListMoviesFragment(numPage)
    }

    private fun setAdapter(view: View, activity: Context, list:List<MoviesModel>) {
        val recyclerView = view.rv_list_movies
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MovieFlixAdapter(list, object:ClickItemListener{
            override fun ClickItemMovie(movie: MoviesModel) {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, DetailMoviesFragment.newInstance(movie))
                    addToBackStack(null)
                    commit()
                }
            }

        })
    }


}
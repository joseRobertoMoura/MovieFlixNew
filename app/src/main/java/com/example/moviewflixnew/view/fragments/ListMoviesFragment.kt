package com.example.moviewflixnew.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.model.MoviesModel
import com.example.moviewflixnew.view.adapter.MovieFlixAdapter
import com.example.moviewflixnew.view.dialog.DialogMessageError
import com.example.moviewflixnew.viewModel.ListMoviesViewModel
import kotlinx.android.synthetic.main.fragment_list_movies.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMoviesFragment(private var numPage: Int) : Fragment(), ClickItemListener{

    lateinit var navController: NavController
    private val listMoviesViewModel: ListMoviesViewModel by viewModel()
    private var totalPages = 0

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
        initViewModel(numPage,view,activity)
    }

    private fun initViewModel(_numpage:Int,view: View, activity: Context) {
        listMoviesViewModel.init(_numpage.toString())
        listMoviesViewModel.moviesList.observe(viewLifecycleOwner, {
            if (it != null){
                setAdapter(view,activity,it)
            }else{
                Toast.makeText(
                    activity,
                    "Ops tivemos um problema, tente novamente mais tarde!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        listMoviesViewModel.total.observe(viewLifecycleOwner, {
            totalPages = it.toInt()
        })
        listMoviesViewModel.errorApi.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()){
                createDialog(it)
            }
        })
    }

    private fun createDialog(message: String) {
        val dialog = DialogMessageError(message)
        dialog.show(parentFragmentManager,dialog.tag)
    }

    companion object {
        fun newInstance(numPage:Int) = ListMoviesFragment(numPage)
    }

    private fun setAdapter(view: View, activity: Context, list:List<MoviesModel>) {
        val recyclerView = view.rv_list_movies
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MovieFlixAdapter(list,this)
    }

    override fun ClickItemMovie(movie: MoviesModel) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, DetailMoviesFragment.newInstance(movie))
            addToBackStack(null)
            commit()
        }
    }
}
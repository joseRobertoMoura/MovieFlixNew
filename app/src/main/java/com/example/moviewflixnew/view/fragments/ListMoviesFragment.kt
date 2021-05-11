package com.example.moviewflixnew.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.model.MoviesModel
import com.example.moviewflixnew.view.adapter.MovieFlixAdapter
import com.example.moviewflixnew.viewModel.ListMoviesViewModel
import kotlinx.android.synthetic.main.fragment_list_movies.*
import kotlinx.android.synthetic.main.fragment_list_movies.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListMoviesFragment : Fragment(), ClickItemListener, View.OnClickListener {

    lateinit var navController: NavController
    private val listMoviesViewModel: ListMoviesViewModel by viewModel()
    private var totalPages = 0
    private var numpage = 1
    lateinit var viewFragment:View
    lateinit var _contextFragment:Context

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
        view.findViewById<ImageView>(R.id.back_pag).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.next_pag).setOnClickListener(this)
        initViewModel(numpage,view,activity)
        setup(view,activity)
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
    }

    companion object {
        fun newInstance() = ListMoviesFragment()
    }

    private fun setAdapter(view: View, activity: Context, list:List<MoviesModel>) {
        val recyclerView = view.rv_list_movies
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = MovieFlixAdapter(list,this)
    }

    override fun ClickItemMovie(movie: MoviesModel) {
        requireFragmentManager().beginTransaction().apply {
            replace(R.id.flFragment, DetailMoviesFragment.newInstance(movie))
            addToBackStack(null)
            commit()
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.back_pag -> {
                if (numpage > 0){
                    numpage -= 1
                    initViewModel(numpage,_getViewFragment(),_getContextFragment())
                }
            }

            R.id.next_pag -> {
                if (numpage <= totalPages){
                    numpage += 1
                    initViewModel(numpage,_getViewFragment(),_getContextFragment())
                }
            }
        }
    }

    private fun setup(view:View, activity: Context){
        this.viewFragment = view
        this._contextFragment = activity
    }

    private fun _getViewFragment():View{
        return this.viewFragment
    }

    private fun _getContextFragment():Context {
        return this._contextFragment
    }
}
package com.example.moviewflixnew.ui.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.view.MainActivity
import com.example.moviewflixnew.ui.view.adapter.MovieFlixAdapter
import com.example.moviewflixnew.ui.viewModel.ListMoviesViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class SearchFragment : Fragment(),ClickItemListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val listMoviesViewModel by viewModels<ListMoviesViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as Context
        initViewModel(view,activity)
    }

    companion object {
        fun newInstance() = SearchFragment()
    }

    private fun initViewModel(view: View, activity: Context) {
        listMoviesViewModel.run {
            init("1")
            moviesList.observe(viewLifecycleOwner, {
                if (it != null){
                    initSearch(it,activity,view)
                }else{
                    Toast.makeText(
                        activity,
                        "Ops tivemos um problema, tente novamente mais tarde!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun initSearch(list: List<MoviesModel>, activity:Context, view:View) {
        sv_search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isEmpty()){
                    setAdapter(view,activity,list)
                }else{
                    searchList(view,activity,list, query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.isEmpty()){
                    setAdapter(view,activity,list)
                }else{
                    searchList(view,activity,list, newText)
                }
                return false
            }
        })
    }

    private fun setAdapter(view: View, activity: Context,list:List<MoviesModel>) {
        rv_list_movies_search.layoutManager = LinearLayoutManager(activity)
        rv_list_movies_search.adapter = MovieFlixAdapter(list,this)
    }

    private fun searchList(view: View,activity: Context,list: List<MoviesModel>, title: String) {
        val listResult: MutableList<MoviesModel> = arrayListOf()
        for (element in list) {
            if (element.original_title?.contains(title, ignoreCase = true) == true) {
                listResult.addAll(listOf(element))
                break
            }
        }

        setAdapter(view,activity,listResult)
    }

    override fun ClickItemMovie(movie: MoviesModel) {
        requireFragmentManager().beginTransaction().apply {
            replace(R.id.flFragment, DetailMoviesFragment.newInstance(movie))
            addToBackStack(null)
            commit()
        }
    }
}
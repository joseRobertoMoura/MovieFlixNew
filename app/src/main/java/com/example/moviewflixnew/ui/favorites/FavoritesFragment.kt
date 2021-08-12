package com.example.moviewflixnew.ui.favorites

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.details.DetailMoviesFragment
import com.example.moviewflixnew.ui.favorites.adapter.FavoritesAdapter
import com.example.moviewflixnew.ui.model.MoviesModel
import javax.inject.Inject

class FavoritesFragment() : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val favoritesViewModel by viewModels<FavoritesViewModel> {viewModelFactory}

    lateinit var recyclerView:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as Context
        initViewModel(view,activity)
    }

    private fun initViewModel(view:View, activity: Context) {
        favoritesViewModel.initList(activity)
        favoritesViewModel.favoritListViewAction.observe(viewLifecycleOwner){ state ->
            when(state){
                is FavoritListActionView.FavoriteListSuccess -> {
                    setAdapter(view,activity,state.success)
                }
                is FavoritListActionView.FavoriteListError -> {
                    Toast.makeText(activity,state.erro, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setAdapter(view: View, activity: Context, list:MutableList<MoviesModel?>) {
        recyclerView = view.findViewById(R.id.rv_list_favorites)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = FavoritesAdapter(list,
            object:ClickItemListener{
            override fun ClickItemMovie(movie: MoviesModel) {
                favoritesViewModel.initDelete(movie,activity)
                Toast.makeText(activity,"Removido com sucesso!", Toast.LENGTH_SHORT).show()
                initViewModel(view,activity)
            }
        },
            object : ClickItemListener {
                override fun ClickItemMovie(movie: MoviesModel) {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, DetailMoviesFragment.newInstance(movie))
                        addToBackStack(null)
                        commit()
                    }
                }

            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }

}
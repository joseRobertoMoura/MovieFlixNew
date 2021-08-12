package com.example.moviewflixnew.ui.listMovies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieflix.model.helper.ClickItemListener
import com.example.moviewflixnew.R
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.MainActivity
import com.example.moviewflixnew.ui.listMovies.adapter.MovieFlixAdapter
import com.example.moviewflixnew.ui.details.DetailMoviesFragment
import com.example.moviewflixnew.ui.utils.dialog.DialogMessageErrorClass
import com.example.moviewflixnew.ui.favorites.FavoritesViewModel
import com.example.moviewflixnew.ui.utils.dialog.MessagesDialogUtilsImpl
import com.example.moviewflixnew.ui.utils.preferences.ManagmentPreferences
import kotlinx.android.synthetic.main.fragment_list_movies.view.*
import javax.inject.Inject

@Suppress("SameParameterValue")
class ListMoviesFragment(
    private var numPage: String
    ) : Fragment(){

    lateinit var navController: NavController
    private var totalPages = 0
    private var _numPage:String = "0"
    @Inject
    lateinit var messagesDialogUtils: MessagesDialogUtilsImpl

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val listMoviesViewModel by viewModels<ListMoviesViewModel> { viewModelFactory }

    private lateinit var btnBack: AppCompatImageView
    private lateinit var btnNext: AppCompatImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var setUserInfo: ManagmentPreferences


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

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
        initView(view,activity)
        initViewModel(numPage, view, activity)
    }

    private fun initView(view: View,context: Context) {
        btnBack = view.findViewById(R.id.back_pag)
        btnNext = view.findViewById(R.id.next_pag)
        progressBar = view.findViewById(R.id.cl_progress_bar)
        setUserInfo = ManagmentPreferences(context)
    }

    private fun initViewModel(numPage:String, view: View, activity: Context) {
        progressBar.visibility = View.VISIBLE
        listMoviesViewModel.init(numPage)
        listMoviesViewModel.moviesList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()){
                setAdapter(view,activity,it)
                changePag(numPage,view,activity)
                progressBar.visibility = View.GONE
            }else{
                Toast.makeText(
                    activity,
                    getString(R.string.Message_error_api),
                    Toast.LENGTH_LONG
                ).show()
                progressBar.visibility = View.GONE
            }
        }
        listMoviesViewModel.total.observe(viewLifecycleOwner) {
            totalPages = it.toInt()
        }
        listMoviesViewModel.errorApi.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                createDialog(activity, R.string.msg_internet_error)
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

    private fun changePag(numPage: String,view: View,activity: Context){
        btnNext.setOnClickListener {
            if (numPage.toInt()<totalPages){
                _numPage = (numPage.toInt() + 1).toString()
                initViewModel(_numPage,view,activity)
            }
        }

        btnBack.setOnClickListener {
            if (numPage.toInt()>1){
                _numPage = (numPage.toInt() -1).toString()
                initViewModel(_numPage,view,activity)
            }
        }
    }

}
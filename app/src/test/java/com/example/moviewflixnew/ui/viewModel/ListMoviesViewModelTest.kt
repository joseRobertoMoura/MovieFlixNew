package com.example.moviewflixnew.ui.viewModel

import androidx.lifecycle.Observer
import com.example.moviewflixnew.InstantTaskExecutorMovieRule
import com.example.moviewflixnew.usecase.tendency.GetListTendencyUseCaseImp
import com.example.moviewflixnew.ui.listMovies.ListMoviesViewModel
import com.example.moviewflixnew.ui.model.MoviesModel
import com.example.moviewflixnew.ui.model.MoviesTendencyModel
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ListMoviesViewModelTest{
    private val useCase: GetListTendencyUseCaseImp = mockk()

    @get:Rule
    val rule = InstantTaskExecutorMovieRule()

    private val testeCoroutineDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: ListMoviesViewModel
    var numPage:String = "1"

    @Mock
    lateinit var observerList:Observer<List<MoviesModel>>


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testeCoroutineDispatcher)

        viewModel = ListMoviesViewModel(useCase)
        viewModel.list.observeForever(observerList)
    }

    @After
    fun cleanUp(){
        Dispatchers.resetMain()
        testeCoroutineDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `when ListViewModel init then it should call the useCase`() {
        GlobalScope.launch {
            withContext(Dispatchers.Unconfined){
                val list: List<MoviesModel> = arrayListOf(MoviesModel(
                    "0",
                    "teste",
                    "teste",
                    "tesete"
                ))

                val mockedList = MoviesTendencyModel(list,null,null)

                val responseMoviesTendency = useCase.getResponseMoviesTendency(numPage)
                coEvery { responseMoviesTendency } returns mockedList

                viewModel.init(numPage)

                coVerify { responseMoviesTendency?.result}
                coVerify { observerList.onChanged(list)}
            }
        }
    }

}

@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
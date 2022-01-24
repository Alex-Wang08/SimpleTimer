package com.example.simpletimer.ui.show_timer

import com.example.simpletimer.MainViewModel
import com.example.simpletimer.data.TimerRepository
import com.nhaarman.mockito_kotlin.mock

import org.junit.Before
import org.junit.Test

class ShowTimerViewModelTest {

    lateinit var viewModel: ShowTimerViewModel
    lateinit var repository: TimerRepository
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        repository = mock()
        mainViewModel = MainViewModel()

        viewModel = ShowTimerViewModel(
            repository = repository,
            mainViewModel = mainViewModel
        )
    }

    @Test
    fun `givenFirstLoad_whenLoadTimersList_thenGetTimerList`() {
        // GIVEN
        viewModel.isFirstLoad = true

        // WHEN
        viewModel.loadTimerList()

        // THEN
        // need to figure out how to test suspend function with mockito
//        verify(repository, times(1)).getTimerList();
    }

    @Test
    fun loadTimerList() {
    }
}
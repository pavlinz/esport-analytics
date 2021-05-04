package by.vasilevskiy.dota2analytics.ui.main

import by.vasilevskiy.dota2analytics.ui.main.viewmodel.MainViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class MainViewModelTest {

    lateinit var mainViewModel : MainViewModel

    @Before
    fun setUp() {
        mainViewModel =
            MainViewModel()
    }

    @After
    fun tearDown() {
        println("tearDown")
    }

    @Test
    fun editTheDate() {
        val receivedData = "April 17, 2021 - 9:15 UTC"
        val expectedData = "April 17, 2021 - 12:15"
        assertEquals(expectedData, mainViewModel.editTheDate(receivedData))
    }
}
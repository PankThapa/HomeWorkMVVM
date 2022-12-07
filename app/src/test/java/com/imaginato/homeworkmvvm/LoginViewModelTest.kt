package com.imaginato.homeworkmvvm

import com.imaginato.homeworkmvvm.data.remote.login.model.request.LoginRequest
import com.imaginato.homeworkmvvm.ui.login.LoginUiState
import com.imaginato.homeworkmvvm.ui.login.LoginViewModel
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {


    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LoginViewModel

    val fakeLoginDataRepository = FakeLoginDataRepository()


    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `string  with empty value should return true`() {
        val result = viewModel.isFieldEmpty("")
        assertTrue(result)
    }

    @Test
    fun `string  with non empty value should return false`() {
        val result = viewModel.isFieldEmpty("abc")
        assertFalse(result)
    }


    @Test
    fun `Success state works for post login api`() = runBlocking {

        val loginRequest = LoginRequest("username", "1111111")

        val response =
            fakeLoginDataRepository.postFakeLoginData(
                imsi = "357175048449937",
                imei = "510110406068589",
                loginRequest = loginRequest
            )
    }

    @Test
    fun `Loading state works for post login api`() = runTest {
        assertEquals(
            LoginUiState.LoginLoadingUiState,
            viewModel.resultLiveData.value
        )
        assertEquals(LoginUiState.LoginLoadingUiState, viewModel.resultLiveData.value)
    }


}
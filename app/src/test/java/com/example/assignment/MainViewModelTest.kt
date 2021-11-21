package com.example.assignment

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.assignment.data.local.ProfileDao
import com.example.assignment.model.MatchProfileResponse
import com.example.assignment.model.ProfileDetails
import com.example.assignment.model.Resource
import com.example.assignment.network.ApiService
import com.example.assignment.ui.main.MainViewModel
import com.example.assignment.util.isNetworkAvailable
import com.tatadigital.tcp.payments.upipayment.paytovpa.data.repository.ISourceRepository
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutinesTestRule()

    @Mock
    private lateinit var sourceRepository: ISourceRepository

    @Mock
    private lateinit var dao: ProfileDao

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }
    @Mock
    private lateinit var profileList: List<ProfileDetails>

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        val response = MatchProfileResponse(profileList)

        val flow = flow {
            emit(Resource.loading())
            emit(Resource.success(response))
        }

        mockkStatic("com.example.assignment.util.ContextExtKt")
        every {
            context.isNetworkAvailable()
        } returns true
        testCoroutineRule.testDispatcher.runBlockingTest {
            `when`(sourceRepository.fetchProfiles(10)).thenReturn(flow)
            `when`(dao.getAll()).thenReturn(emptyList())
        }

        testCoroutineRule.testDispatcher.pauseDispatcher()

        val viewModel = MainViewModel(sourceRepository)
        viewModel.fetchProfiles()
        assertThat(viewModel.stateFlow.value, `is`(Resource.loading()))

        testCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(viewModel.stateFlow.value, `is`(Resource.success(response)))
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        val errorMsg = Exception("errorMsg")
        val flow = flow<Resource<MatchProfileResponse>> {
            emit(Resource.loading())
            emit(Resource.error(errorMsg))
        }
        mockkStatic("com.example.assignment.util.ContextExtKt")
        every {
            context.isNetworkAvailable()
        } returns true
        testCoroutineRule.testDispatcher.runBlockingTest {
            `when`(sourceRepository.fetchProfiles(10)).thenReturn(flow)
            `when`(dao.getAll()).thenReturn(emptyList())
        }
        testCoroutineRule.testDispatcher.pauseDispatcher()
        val viewModel = MainViewModel(sourceRepository)
        viewModel.fetchProfiles()
        assertThat(viewModel.stateFlow.value, `is`(Resource.loading()))

        testCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(viewModel.stateFlow.value, `is`(Resource.error(errorMsg, null)))
    }

    @Test
    fun givenNetworkUnAvailable_whenFetch_shouldReturnError() {
        val errorMsg = Exception(context.getString(R.string.failed_network_msg))
        val response = MatchProfileResponse(profileList)
        val flow = flow {
            emit(Resource.loading())
            emit(Resource.error(errorMsg, response))
        }

        mockkStatic("com.example.assignment.util.ContextExtKt")
        every {
            context.isNetworkAvailable()
        } returns false
        testCoroutineRule.testDispatcher.runBlockingTest {
            `when`(sourceRepository.fetchProfiles(10)).thenReturn(flow)
            `when`(dao.getAll()).thenReturn(profileList)
        }

        testCoroutineRule.testDispatcher.pauseDispatcher()

        val viewModel = MainViewModel(sourceRepository)
        viewModel.fetchProfiles()

        assertThat(viewModel.stateFlow.value, `is`(Resource.loading()))

        testCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(viewModel.stateFlow.value, `is`(Resource.error(errorMsg, response)))
    }
}
package com.example.trackt.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.trackt.application.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TracktViewModel(
    private val usersRepository: UsersRepository,
    destinationRepository: DestinationRepository): ViewModel() {

    var signupUIState by mutableStateOf(SignupUIState()) //for signup
    var loginUIState by mutableStateOf(LoginUIState()) //for login

    private var yourToken: String? = null
    //var name: String? = null

    private fun validateUserInput(uiState: UserFullDetails = signupUIState.userDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && email.isNotBlank()  && password.isNotBlank() && confirmPassword.isNotBlank()
                    && password == confirmPassword
        }
    }
    fun updateUiState(userDetails: UserFullDetails) {
        signupUIState =
            SignupUIState(userDetails = userDetails, isEntryValid = validateUserInput(userDetails))
    }
    fun createUser() {
        viewModelScope.launch {
            usersRepository.createUser(signupUIState.userDetails.toUser())
        }
    }

    private fun validateLoginInput(uiState: UserLoginDetails = loginUIState.userLoginDetails): Boolean {
        return with(uiState) {
            email.isNotBlank()  && password.isNotBlank()
        }
    }
    fun updateLoginUiState(userLoginDetails: UserLoginDetails) {
        loginUIState =
            LoginUIState(userLoginDetails = userLoginDetails, isEntryValid = validateLoginInput(userLoginDetails))
    }
    fun getUser(context: Context){
        val sessionManger = SessionManager(context)
        viewModelScope.launch {
            val token = usersRepository.getUser(loginUIState.userLoginDetails.toLogin())

            Log.v("Login token", "${token.body()!!.accessToken} Hello, something happened")

            if (token.body()?.accessToken  != null) {
                sessionManger.saveAuthToken(token.body()!!.accessToken)
                //val theToken = sessionManger.fetchAuthToken()
                val theToken = token.body()!!.accessToken
                setToken(theToken)
                Log.v("The token", "$theToken Here's your token")
            }
        }
    }

//    private val sessionManager: SessionManager = SessionManager(sContext)
//    private val yourToken = sessionManager.fetchAuthToken()

    val travelsListUIState: StateFlow<TravelsUIState> =
        destinationRepository.getDestinations(yourToken).map { TravelsUIState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TravelsUIState()
            )
    companion object{
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private fun setToken(token: String?){
        yourToken = token
    }
    //private fun setName(yourName: String){
      //  name = yourName
    //}
}

data class SignupUIState(
    val userDetails: UserFullDetails = UserFullDetails(),
    var isEntryValid : Boolean = false
)
data class UserFullDetails(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = ""
) {
    fun toUser(): Models.User = Models.User(
        name = name,
        email = email,
        password = password
    )
}

data class  LoginUIState(
    val userLoginDetails: UserLoginDetails = UserLoginDetails(),
    var isEntryValid : Boolean = false
)
data class UserLoginDetails(
    var email: String = "",
    var password: String = ""
){
    fun toLogin(): Models.User = Models.User(
        name = "",
        email = email,
        password = password
    )
}

data class TravelsUIState(
    val travelsList: List<Models.DestinationResponse> = listOf()
)

object AppViewModelProvider {

    private lateinit var applicationContext: ApplicationContext

    fun initialize(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    fun createViewModelInstance() : ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val usersRepository = UsersRepository()
                val destinationRepository = DestinationRepository()
                //val context: Context = ApplicationContext()
                if (modelClass.isAssignableFrom(TracktViewModel::class.java)) {
                    return TracktViewModel(usersRepository, destinationRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }

        }
    }
}
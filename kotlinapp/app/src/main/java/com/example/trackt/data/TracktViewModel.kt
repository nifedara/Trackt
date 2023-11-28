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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TracktViewModel(
    private val usersRepository: UsersRepository,
    private val destinationRepository: DestinationRepository): ViewModel() {

    var signupUIState by mutableStateOf(SignupUIState()) //for signup
    var loginUIState by mutableStateOf(LoginUIState()) //for login
    var travelsUIState by mutableStateOf(TravelsUIState()) //for travels

    private var yourToken: String? = null

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
            val signupResponse = usersRepository.createUser(signupUIState.userDetails.toUser())
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

    //status
//    private val _status = MutableStateFlow(false)
//    val status: StateFlow<Boolean> = _status.asStateFlow()
//
//    //name
//    private val _name = MutableStateFlow("")
//    val name: StateFlow<String> = _name.asStateFlow()
    fun getUser(context: Context){
        val sessionManger = SessionManager(context)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val loginResponse = usersRepository.getUser(loginUIState.userLoginDetails.toLogin())
                val token = loginResponse.body()!!.data.token
                val userName = loginResponse.body()!!.data.userInfo.name
                val status = loginResponse.body()!!.status

                if (status){
                    loginUIState = loginUIState.copy(isUserLoggedIn = true)
                }

                Log.v("Login token", "$token Hello, something happened")
                Log.v("Name", "$userName Hello, something happened")

                sessionManger.saveAuthToken(token)
                setToken(token)
            }
        }
    }

//    private val sessionManager: SessionManager = SessionManager(sContext)
//    private val yourToken = sessionManager.fetchAuthToken()

//    val travelsListUIState: StateFlow<TravelsUIState> =
//        destinationRepository.getDestinations(yourToken).map { TravelsUIState(it) }
//            .stateIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//                initialValue = TravelsUIState()
//            )
//    companion object{
//        private const val TIMEOUT_MILLIS = 5_000L
//    }

    private val _travelsState = MutableStateFlow(TravelsUIState())
    val travelsState: StateFlow<TravelsUIState> = _travelsState.asStateFlow()

    init {
        getDestinations()
    }
        private fun getDestinations() {
            viewModelScope.launch {
                if (yourToken != null){
                    val getDestinationsResponse = destinationRepository.getDestinations(yourToken!!)
                    val destinations = getDestinationsResponse.data
                    _travelsState.value = destinations
                }
            }
        }

    private fun setToken(token: String?){
        yourToken = token
    }
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

data class LoginUIState(
    val userLoginDetails: UserLoginDetails = UserLoginDetails(),
    var isEntryValid : Boolean = false,
    val isUserLoggedIn: Boolean = false,
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
    val travelsList: List<Models.DestinationResponse> = emptyList(),
    val userName: String? = ""
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
                if (modelClass.isAssignableFrom(TracktViewModel::class.java)) {
                    return TracktViewModel(usersRepository, destinationRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }

        }
    }
}
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
import kotlinx.coroutines.launch

class TracktViewModel(
    private val usersRepository: UsersRepository,
    private val destinationRepository: DestinationRepository): ViewModel() {

    var userUIState by mutableStateOf(UserUIState())
    var loginUIState by mutableStateOf(LoginUIState()) //for login

    private fun validateUserInput(uiState: UserFullDetails = userUIState.userDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && email.isNotBlank()  && password.isNotBlank() && confirmPassword.isNotBlank()
                    && password == confirmPassword
        }
    }
    fun updateUiState(userDetails: UserFullDetails) {
        userUIState =
            UserUIState(userDetails = userDetails, isEntryValid = validateUserInput(userDetails))
    }
    fun createUser() {
        viewModelScope.launch {
            usersRepository.createUser(userUIState.userDetails.toUser())
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
        val sessionManger: SessionManager = SessionManager(context)
        viewModelScope.launch {
            val token = usersRepository.getUser(loginUIState.userLoginDetails.toLogin())

            Log.v("Login token", "${token.body()!!.accessToken} Hello, something happened")

            if (token.body()?.accessToken  != null) {
                sessionManger.saveAuthToken(token.body()!!.accessToken)
            }


        }
    }


}

data class UserUIState(
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
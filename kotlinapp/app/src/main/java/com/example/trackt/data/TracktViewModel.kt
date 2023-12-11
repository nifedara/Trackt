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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


class TracktViewModel(
    private val usersRepository: UsersRepository,
    private val destinationRepository: DestinationRepository): ViewModel() {

    var signupUIState by mutableStateOf(SignupUIState()) //for signup
    var loginUIState by mutableStateOf(LoginUIState(token = "")) //for login
    var travelsUIState by mutableStateOf(TravelsUIState()) //for travels
    var destinationUIState by mutableStateOf(DestinationUIState()) //for travels

    private var yourToken: String? = null


    //create new destination
    fun updateDestinationForm(destinationDetails: DestinationDetails){
        destinationUIState =
            DestinationUIState(destinationDetails = destinationDetails)
    }
    //create destination
    fun createDestination(token: String){
        viewModelScope.launch {
            Log.v("Coroutine", "Started")
            try {
                val destinationName = MultipartBody.Part.createFormData("destinationName", null,
                    destinationUIState.destinationDetails.destinationName.toRequestBody(null))
                val image = destinationUIState.destinationDetails.image?.let {
                    val reqFile = it.asRequestBody("image/*".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData("image", it.name, reqFile)
                }
                val budget = MultipartBody.Part.createFormData("budget", null,
                    destinationUIState.destinationDetails.budget.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
                val date = MultipartBody.Part.createFormData("date", null,
                    destinationUIState.destinationDetails.date.toRequestBody(null)
                )

                val createDestinationResponse = image?.let {
                    destinationRepository.createDestination(destinationName, it, budget, date, token) }

                //val createDestinationResponse = destinationRepository.createDestination(destinationUIState.destinationDetails.toDestination(), token)
                Log.v("image contains this:", destinationUIState.destinationDetails.image.toString())
                Log.v("i got here", true.toString())
                Log.v("destination response", createDestinationResponse.toString())
                if (createDestinationResponse != null) {
                    Log.v("destination response status", createDestinationResponse.body()?.status.toString())
                }
                if (createDestinationResponse != null) {
                    Log.v("destination response message", createDestinationResponse.body()?.message.toString())
                }
            } catch (e: Exception) {
                Log.e("Coroutine Exception", e.toString())
            }
        }
    }


    //create user
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
    //create a new user
    fun createUser() {
        viewModelScope.launch {
            try {
                signupUIState = signupUIState.copy(isLoading = true)

                val signupResponse = usersRepository.createUser(signupUIState.userDetails.toUser())
                val status = signupResponse.body()!!.status
                val message = signupResponse.body()!!.message

                signupUIState = signupUIState.copy(status = status, message = message)
                if (!status) { //reset isLoading
                    signupUIState = signupUIState.copy(isLoading = false)
                }
            }
            catch (e: Exception) {
                Log.e("Coroutine Exception", e.toString())
            }
            finally {
                signupUIState = signupUIState.copy(isLoading = false)
            }
        }
    }


    //log in user
    private fun validateLoginInput(uiState: UserLoginDetails = loginUIState.userLoginDetails): Boolean {
        return with(uiState) {
            email.isNotBlank()  && password.isNotBlank()
        }
    }
    fun updateLoginUiState(userLoginDetails: UserLoginDetails) {
        loginUIState =
            LoginUIState(userLoginDetails = userLoginDetails, isEntryValid = validateLoginInput(userLoginDetails), token = "")
    }
    //get user
    fun getUser(context: Context){
        val sessionManger = SessionManager(context)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    loginUIState = loginUIState.copy(isLoading = true)
                    val loginResponse =
                        usersRepository.getUser(loginUIState.userLoginDetails.toLogin())
                    val status = loginResponse.body()!!.status
                    val message = loginResponse.body()!!.message

                    loginUIState = loginUIState.copy(status = status, message = message)

                    if (status){
                        val token = loginResponse.body()!!.data.token
                        val userName = loginResponse.body()!!.data.userInfo.name

                        loginUIState = loginUIState.copy(
                            isUserLoggedIn = true,
                            token = token,
                            userName = userName
                        )

                        Log.v("Login token", "$token Hello, something happened")
                        Log.v("loginUIState", "$loginUIState Hello, login UI State")

                        sessionManger.saveAuthToken(token)
                        setToken(token)
                    }
                }
                catch (e: Exception) {
                    Log.e("Coroutine Exception", e.toString())
                }
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

    //display destinations
    private val _travelsState = MutableStateFlow(TravelsUIState())
    val travelsState: StateFlow<TravelsUIState> = _travelsState.asStateFlow()
    //private val _travelsState = MutableStateFlow<TravelsUIState?>(null)

    //get destinations
    fun getDestinations(token: String) {
        viewModelScope.launch {
            //val getDestinationsResponse = destinationRepository.getDestinations(loginUIState.token)
            val getDestinationsResponse = destinationRepository.getDestinations((token))
            val destinations = getDestinationsResponse.data
            _travelsState.value = TravelsUIState(travelsList = destinations)
        }
    }

//    init {
//        if (loginUIState.token.isNotBlank()){
//            getDestinations()
//        }
//    }

    private fun setToken(token: String?){
        yourToken = token
    }
}

//create destination UI state
data class DestinationUIState(
    val destinationDetails: DestinationDetails = DestinationDetails()
)
data class DestinationDetails(
    val destinationName: String = "",
    val image: File? = null,
    val budget: Double = 0.0,
    val date: String = ""
)


//Sign up UI state
data class SignupUIState(
    val userDetails: UserFullDetails = UserFullDetails(),
    var isEntryValid: Boolean = false,
    var status: Boolean? = null,
    var message: String? = null,
    var isLoading: Boolean = false
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
    ) }


//Login UI state
data class LoginUIState(
    val userLoginDetails: UserLoginDetails = UserLoginDetails(),
    var isEntryValid : Boolean = false,
    var status: Boolean? = null,
    var message: String? = null,
    var isLoading: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    var token: String,
    val userName: String? = ""
)
data class UserLoginDetails(
    var email: String = "",
    var password: String = ""
){
    fun toLogin(): Models.User = Models.User(
        name = "",
        email = email,
        password = password
    ) }


//Travel UI state
data class TravelsUIState(
    var travelsList: List<Models.DestinationResponse.Destination> = emptyList(),
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
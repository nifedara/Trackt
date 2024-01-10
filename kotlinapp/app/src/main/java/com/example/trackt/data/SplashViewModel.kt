package com.example.trackt.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackt.ui.screens.LoginScreen
import com.example.trackt.ui.screens.WelcomeScreen
import kotlinx.coroutines.launch
import javax.inject.Inject


///Splash screen viewModel
class SplashViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(WelcomeScreen.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            dataStoreRepository.readOnboardingState().collect { completed ->
                if (completed){
                    _startDestination.value = LoginScreen.route
                } else {
                    _startDestination.value = WelcomeScreen.route
                }
                _isLoading.value = false
            }
        }
    }
}
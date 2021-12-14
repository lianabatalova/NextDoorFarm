package tech.volkov.nextdoorfarm.backend.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerAuthRepositoryComponent
import tech.volkov.nextdoorfarm.backend.model.UserLogInDto
import tech.volkov.nextdoorfarm.backend.model.UserType
import tech.volkov.nextdoorfarm.backend.repository.AuthRepository
import tech.volkov.nextdoorfarm.frontend.view.LogInSignUpView
import javax.inject.Inject

@InjectViewState
class LogInPresenter : MvpPresenter<LogInSignUpView>() {

    @Inject
    lateinit var authRepository: AuthRepository

    init {
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    fun logInUserBy(userName: String, password: String, userType: UserType) = uiScope.launch {
        val userLogInDto = UserLogInDto(userName, password, userType)
        when (val user = authRepository.logInUser(userLogInDto)) {
            null -> viewState.showErrorMessage(AUTH_ERROR_MESSAGE)
            else -> viewState.logInToApplication(user)
        }
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val AUTH_ERROR_MESSAGE = "Пользователя с таким логином и паролем не существует"
    }
}

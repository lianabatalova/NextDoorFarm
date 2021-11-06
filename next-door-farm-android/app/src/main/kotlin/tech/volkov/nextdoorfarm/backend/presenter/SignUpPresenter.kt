package tech.volkov.nextdoorfarm.backend.presenter

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerAuthRepositoryComponent
import tech.volkov.nextdoorfarm.backend.helper.Validation
import tech.volkov.nextdoorfarm.backend.model.UserSignInDto
import tech.volkov.nextdoorfarm.backend.model.UserType
import tech.volkov.nextdoorfarm.backend.repository.mock.AuthRepositoryMock
import tech.volkov.nextdoorfarm.frontend.view.LogInSignUpView
import javax.inject.Inject

@InjectViewState
class SignUpPresenter: MvpPresenter<LogInSignUpView>() {

    @Inject
    lateinit var authRepository: AuthRepositoryMock

    init {
        DaggerAuthRepositoryComponent.create().inject(this)
    }

    fun signUpUser(
        firstName: String,
        lastName: String,
        userName: String,
        email: String,
        password: String,
        userType: UserType
    ) = uiScope.launch {
        if (Validation.isEmailValid(email)) {
            val userSignInDto = UserSignInDto(firstName, lastName, userName, email, password, userType)
            when (val user = authRepository.signUpUser(userSignInDto)) {
                null -> viewState.showErrorMessage(ADD_USER_ERROR)
                else -> viewState.logInToApplication(user)
            }
        } else {
            viewState.showErrorMessage(EMAIL_VALIDATION_ERROR)
        }
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val ADD_USER_ERROR = "Что-то пошло не так. Не получилось добавить нового пользователя в базу данных"
        private const val EMAIL_VALIDATION_ERROR = "Введенный email является некорректным"
    }
}

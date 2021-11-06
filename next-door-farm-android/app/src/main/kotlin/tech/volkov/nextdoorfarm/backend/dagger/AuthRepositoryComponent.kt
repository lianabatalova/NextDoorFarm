package tech.volkov.nextdoorfarm.backend.dagger

import dagger.Component
import tech.volkov.nextdoorfarm.backend.presenter.LogInPresenter
import tech.volkov.nextdoorfarm.backend.presenter.SignUpPresenter

@Component
interface AuthRepositoryComponent {
    fun inject(loginPresenter: LogInPresenter)
    fun inject(signUpPresenter: SignUpPresenter)
}

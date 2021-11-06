package tech.volkov.nextdoorfarm.frontend.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import tech.volkov.nextdoorfarm.backend.model.UserLoggedInDto

@StateStrategyType(AddToEndSingleStrategy::class)
interface LogInSignUpView: MvpView {

    fun logInToApplication(user: UserLoggedInDto)

    fun showErrorMessage(message: String)
}

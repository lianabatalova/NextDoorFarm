package tech.volkov.nextdoorfarm.frontend.view.internal

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto

@StateStrategyType(OneExecutionStateStrategy::class)
interface CustomerProfileEditView : MvpView {

    fun fillInUserInfo(user: CustomerAndOrdersDto)

    fun showSuccessMessage(message: String)

    fun openCustomerProfileFragment()

    fun showErrorMessage(message: String)
}

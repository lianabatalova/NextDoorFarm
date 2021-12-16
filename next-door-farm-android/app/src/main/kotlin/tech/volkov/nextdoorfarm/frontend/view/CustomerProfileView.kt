package tech.volkov.nextdoorfarm.frontend.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto

@StateStrategyType(AddToEndSingleStrategy::class)
interface CustomerProfileView : MvpView {

    fun fillInUserProfile(user: CustomerAndOrdersDto)

    fun showErrorMessage(message: String)
}

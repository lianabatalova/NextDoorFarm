package tech.volkov.nextdoorfarm.frontend.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto
import tech.volkov.nextdoorfarm.backend.model.FarmerDto

@StateStrategyType(AddToEndSingleStrategy::class)
interface CustomerProductsView : MvpView {

    fun fillInProducts(products: List<FarmerDto>?)

    fun showErrorMessage(message: String)
}

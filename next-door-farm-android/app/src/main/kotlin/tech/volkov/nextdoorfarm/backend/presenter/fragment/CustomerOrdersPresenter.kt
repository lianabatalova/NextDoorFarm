package tech.volkov.nextdoorfarm.backend.presenter.fragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerCustomerRepositoryComponent
import tech.volkov.nextdoorfarm.backend.repository.CustomerRepository
import tech.volkov.nextdoorfarm.frontend.view.CustomerOrdersView
import javax.inject.Inject

@InjectViewState
class CustomerOrdersPresenter : MvpPresenter<CustomerOrdersView>() {

    @Inject
    lateinit var customerRepository: CustomerRepository

    init {
        DaggerCustomerRepositoryComponent.create().inject(this)
    }

    fun fillInOrders(bearerAuth: String) = uiScope.launch {
        when (val user = customerRepository.getCustomer(bearerAuth)) {
            null -> viewState.showErrorMessage(USER_GET_ERROR)
            else -> viewState.fillInOrders(user.orders)
        }
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val USER_GET_ERROR = "Failed to load orders"
    }
}

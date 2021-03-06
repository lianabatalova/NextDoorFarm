package tech.volkov.nextdoorfarm.backend.presenter.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerCustomerRepositoryComponent
import tech.volkov.nextdoorfarm.backend.model.CustomerDto
import tech.volkov.nextdoorfarm.backend.repository.CustomerRepository
import tech.volkov.nextdoorfarm.frontend.view.internal.CustomerProfileEditView
import javax.inject.Inject

@InjectViewState
class CustomerProfileEditPresenter : MvpPresenter<CustomerProfileEditView>() {

    @Inject
    lateinit var customerRepository: CustomerRepository

    init {
        DaggerCustomerRepositoryComponent.create().inject(this)
    }

    fun getCustomer(bearerAuth: String) = uiScope.launch {
        when (val user = customerRepository.getCustomer(bearerAuth)) {
            null -> viewState.showErrorMessage(USER_GET_ERROR)
            else -> viewState.fillInUserInfo(user)
        }
    }

    fun updateCustomer(
        token: String,
        firstName: String,
        lastName: String,
        address: String,
        username: String,
        email: String,
        phone: String
    ) = uiScope.launch {
        val customerDto = CustomerDto(
            firstName = firstName,
            secondName = lastName,
            address = address,
            username = username,
            email = email,
            phone = phone
        )
        when (val user = customerRepository.updateCustomer(token, customerDto)) {
            null -> viewState.showErrorMessage(USER_GET_ERROR)
            else -> viewState.fillInUserInfo(user)
        }

        viewState.openCustomerProfileFragment()
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val USER_GET_ERROR = "Failed to load customer"
    }
}

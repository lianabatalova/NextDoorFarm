package tech.volkov.nextdoorfarm.backend.presenter.fragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerCustomerRepositoryComponent
import tech.volkov.nextdoorfarm.backend.repository.CustomerRepository
import tech.volkov.nextdoorfarm.frontend.view.ProfileView
import javax.inject.Inject

@InjectViewState
class CustomerProfilePresenter : MvpPresenter<ProfileView>() {

    @Inject
    lateinit var customerRepository: CustomerRepository

    init {
        DaggerCustomerRepositoryComponent.create().inject(this)
    }

    fun getCustomer(bearerAuth: String) = uiScope.launch {
        when (val user = customerRepository.getCustomer(bearerAuth)) {
            null -> viewState.showErrorMessage(USER_GET_ERROR)
            else -> viewState.fillInUserProfile(user)
        }
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val USER_GET_ERROR = "Не удалось загрузить пользователя"
    }
}

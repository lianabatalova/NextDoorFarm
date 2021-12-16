package tech.volkov.nextdoorfarm.backend.presenter.fragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerCustomerRepositoryComponent
import tech.volkov.nextdoorfarm.backend.dagger.DaggerFarmerRepositoryComponent
import tech.volkov.nextdoorfarm.backend.repository.FarmersRepository
import tech.volkov.nextdoorfarm.frontend.view.CustomerProfileView
import tech.volkov.nextdoorfarm.frontend.view.FarmerProfileView
import javax.inject.Inject

@InjectViewState
class FarmerProfilePresenter : MvpPresenter<FarmerProfileView>() {

    @Inject
    lateinit var farmersRepository: FarmersRepository

    init {
        DaggerFarmerRepositoryComponent.create().inject(this)
    }

    fun getFarmer(bearerAuth: String) = uiScope.launch {
        when (val user = farmersRepository.getFarmer(bearerAuth)) {
            null -> viewState.showErrorMessage(USER_GET_ERROR)
            else -> viewState.fillInUserProfile(user)
        }
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val USER_GET_ERROR = "Failed to load farmer"
    }
}

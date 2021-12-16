package tech.volkov.nextdoorfarm.backend.presenter.fragment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import tech.volkov.nextdoorfarm.backend.dagger.DaggerFarmerRepositoryComponent
import tech.volkov.nextdoorfarm.backend.repository.FarmersRepository
import tech.volkov.nextdoorfarm.frontend.view.CustomerProductsView
import javax.inject.Inject

@InjectViewState
class CustomerProductPresenter : MvpPresenter<CustomerProductsView>() {

    @Inject
    lateinit var farmersRepository: FarmersRepository

    init {
        DaggerFarmerRepositoryComponent.create().inject(this)
    }

    fun fillInProducts() = uiScope.launch {
        when (val products = farmersRepository.getAllProducts()) {
            null -> viewState.showErrorMessage(USER_GET_ERROR)
            else -> viewState.fillInProducts(products)
        }
    }

    companion object {
        private val uiScope = CoroutineScope(Dispatchers.Main)
        private const val USER_GET_ERROR = "Failed to load products"
    }
}

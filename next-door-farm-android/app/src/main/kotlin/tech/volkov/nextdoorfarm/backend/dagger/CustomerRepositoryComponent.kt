package tech.volkov.nextdoorfarm.backend.dagger

import dagger.Component
import tech.volkov.nextdoorfarm.backend.presenter.fragment.CustomerOrdersPresenter
import tech.volkov.nextdoorfarm.backend.presenter.fragment.CustomerProfilePresenter
import tech.volkov.nextdoorfarm.backend.presenter.internal.CustomerProfileEditPresenter

@Component
interface CustomerRepositoryComponent {

    fun inject(customerProfilePresenter: CustomerProfilePresenter)

    fun inject(customerProfileEditPresenter: CustomerProfileEditPresenter)

    fun inject(customerOrdersPresenter: CustomerOrdersPresenter)
}

package tech.volkov.nextdoorfarm.backend.dagger

import dagger.Component
import tech.volkov.nextdoorfarm.backend.presenter.LogInPresenter
import tech.volkov.nextdoorfarm.backend.presenter.SignUpPresenter
import tech.volkov.nextdoorfarm.backend.presenter.fragment.CustomerProductPresenter
import tech.volkov.nextdoorfarm.backend.presenter.fragment.FarmerProfilePresenter

@Component
interface FarmerRepositoryComponent {

    fun inject(customerProductPresenter: CustomerProductPresenter)

    fun inject(farmerProfilePresenter: FarmerProfilePresenter)
}

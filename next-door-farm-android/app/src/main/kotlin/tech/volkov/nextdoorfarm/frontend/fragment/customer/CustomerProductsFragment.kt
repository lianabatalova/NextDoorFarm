package tech.volkov.nextdoorfarm.frontend.fragment.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_customer_products.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.FarmerDto
import tech.volkov.nextdoorfarm.backend.presenter.fragment.CustomerProductPresenter
import tech.volkov.nextdoorfarm.frontend.fragment.adapter.ProductItemAdapter
import tech.volkov.nextdoorfarm.frontend.view.CustomerProductsView

class CustomerProductsFragment : MvpAppCompatFragment(), CustomerProductsView {

    private lateinit var mainActivity: AppCompatActivity
    private lateinit var currentView: View

    @InjectPresenter
    lateinit var customerProductPresenter: CustomerProductPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customer_products, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        mainActivity = activity as AppCompatActivity
        mainActivity.supportActionBar?.title =
            resources.getString(R.string.fragment_customer_products_title)
        currentView = view
        setOnClickListeners(view)
        customerProductPresenter.fillInProducts()
    }

    private fun setOnClickListeners(view: View) {
//        view.appointmentsAddAppointmentButton.setOnClickListener {
//            fragmentManager
//                ?.beginTransaction()
//                ?.replace(R.id.mainContainer, AddAppointmentFragment())
//                ?.commit()
//        }
    }

    override fun fillInProducts(farmers: List<FarmerDto>?) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        currentView.productsRecyclerView.layoutManager = layoutManager
        val products = farmers!!.map { it.products }.flatten()

        val adapter = ProductItemAdapter(requireActivity(), products)
        currentView.productsRecyclerView.adapter = adapter
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(mainActivity, message, Toast.LENGTH_SHORT, true).show()
    }

//    override fun openAddAppointmentPage() {
//        fragmentManager
//            ?.beginTransaction()
//            ?.replace(R.id.mainContainer, AppointmentDetailsFragment())
//            ?.commit()
//    }
}

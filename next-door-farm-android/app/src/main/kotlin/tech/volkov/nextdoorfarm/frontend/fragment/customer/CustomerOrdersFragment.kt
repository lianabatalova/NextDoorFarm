package tech.volkov.nextdoorfarm.frontend.fragment.customer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_customer_orders.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.CustomerOrder
import tech.volkov.nextdoorfarm.backend.presenter.fragment.CustomerOrdersPresenter
import tech.volkov.nextdoorfarm.frontend.fragment.adapter.OrderItemAdapter
import tech.volkov.nextdoorfarm.frontend.view.CustomerOrdersView

class CustomerOrdersFragment : MvpAppCompatFragment(), CustomerOrdersView {

    private lateinit var mainActivity: AppCompatActivity
    private lateinit var currentView: View

    @InjectPresenter
    lateinit var customerOrdersPresenter: CustomerOrdersPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customer_orders, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        mainActivity = activity as AppCompatActivity
        mainActivity.supportActionBar?.title =
            resources.getString(R.string.fragment_customer_orders_title)
        currentView = view
        setOnClickListeners(view)

        val token = getSharedPreferences().getString(getString(R.string.token), "")!!
        val tempToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjBlZjc0MDM4LWQ5ZGQtNGEwYi1hMmNlLTIyNTYzOGE2OGIzYiIsInVzZXJuYW1lIjoiaXZhbiIsInVzZXJUeXBlIjoiY3VzdG9tZXIiLCJleHAiOjE2Mzk2NzA5MzksImlzcyI6Ik5leHREb29yRmFybSIsImF1ZCI6Ik5leHREb29yRmFybSJ9.24CymazaTGApWXo2lrhhuIP46GiE9Gp0agp5PUg89PE"
        customerOrdersPresenter.fillInOrders(tempToken)
    }

    private fun getSharedPreferences(): SharedPreferences {
        val fileKey = getString(R.string.preference_file_key)
        return mainActivity.getSharedPreferences(fileKey, Context.MODE_PRIVATE)
    }

    private fun setOnClickListeners(view: View) {
//        view.appointmentsAddAppointmentButton.setOnClickListener {
//            fragmentManager
//                ?.beginTransaction()
//                ?.replace(R.id.mainContainer, AddAppointmentFragment())
//                ?.commit()
//        }
    }

    override fun fillInOrders(orders: List<CustomerOrder>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        currentView.ordersRecyclerView.layoutManager = layoutManager

        val adapter = OrderItemAdapter(requireActivity(), orders)
        currentView.ordersRecyclerView.adapter = adapter
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(mainActivity, message, Toast.LENGTH_SHORT, true).show()
    }
}

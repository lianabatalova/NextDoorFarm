package tech.volkov.nextdoorfarm.frontend.fragment.customer

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_customer_profile.*
import kotlinx.android.synthetic.main.fragment_customer_profile.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto
import tech.volkov.nextdoorfarm.backend.presenter.fragment.CustomerProfilePresenter
import tech.volkov.nextdoorfarm.frontend.fragment.customer.internal.CustomerProfileEditFragment
import tech.volkov.nextdoorfarm.frontend.view.ProfileView
import kotlin.system.exitProcess

class CustomerCartFragment : MvpAppCompatFragment(), ProfileView {

    private lateinit var mainActivity: AppCompatActivity

    @InjectPresenter
    lateinit var customerProfilePresenter: CustomerProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customer_cart, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        mainActivity = activity as AppCompatActivity
        mainActivity.supportActionBar?.title =
            resources.getString(R.string.fragment_customer_profile_title)
        setOnClickListeners(view)

        customerProfilePresenter.getCustomer()
    }

    private fun setOnClickListeners(view: View) {
        view.customerProfileLogOutButton.setOnClickListener {
            logOutFromSharedPref()
            exitProcess(0)
        }

        view.customerProfileEditButton.setOnClickListener {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.mainContainer, CustomerProfileEditFragment())
                ?.commit()
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun logOutFromSharedPref() {
        getSharedPreferences().edit().clear().commit()
    }

    private fun getSharedPreferences(): SharedPreferences {
        val fileKey = getString(R.string.preference_file_key)
        return mainActivity.getSharedPreferences(fileKey, Context.MODE_PRIVATE)
    }

    @SuppressLint("SetTextI18n")
    override fun fillInUserProfile(user: CustomerAndOrdersDto) {
        profileFullName.text = "${user.firstName} ${user.lastName}"
        customerProfileUsername.text = user.username
        customerProfileAddress.text = user.address
        customerProfileEmail.text = user.email
        customerProfilePhone.text = user.phone
        customerProfileUserType.text = "Покупатель"
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(mainActivity, message, Toast.LENGTH_SHORT, true).show()
    }
}

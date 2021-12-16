package tech.volkov.nextdoorfarm.frontend.fragment.farmer

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
import kotlinx.android.synthetic.main.fragment_farmer_profile.*
import kotlinx.android.synthetic.main.fragment_farmer_profile.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.FarmerDto
import tech.volkov.nextdoorfarm.backend.presenter.fragment.FarmerProfilePresenter
import tech.volkov.nextdoorfarm.frontend.fragment.customer.internal.CustomerProfileEditFragment
import tech.volkov.nextdoorfarm.frontend.view.FarmerProfileView
import kotlin.system.exitProcess

class FarmerProfileFragment : MvpAppCompatFragment(), FarmerProfileView {

    private lateinit var mainActivity: AppCompatActivity

    @InjectPresenter
    lateinit var farmerProfilePresenter: FarmerProfilePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_farmer_profile, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        mainActivity = activity as AppCompatActivity
        mainActivity.supportActionBar?.title =
            resources.getString(R.string.fragment_customer_profile_title)
        setOnClickListeners(view)

        val token = getSharedPreferences().getString(getString(R.string.token), "")!!
        farmerProfilePresenter.getFarmer(token)
    }

    private fun setOnClickListeners(view: View) {
        view.farmerProfileLogOutButton.setOnClickListener {
            logOutFromSharedPref()
            exitProcess(0)
        }

        view.farmerProfileEditButton.setOnClickListener {
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
    override fun fillInUserProfile(user: FarmerDto) {
        farmerProfileFullName.text = "${user.firstName} ${user.secondName}"
        farmerProfileUsername.text = user.username
        farmerProfileAddress.text = user.address
        farmerProfileEmail.text = user.email
        farmerProfilePhone.text = user.phone
        farmerProfileDescription.text = user.description
        farmerProfileRating.text = user.rating.toString()
        farmerProfileUserType.text = "Farmer"
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(mainActivity, message, Toast.LENGTH_SHORT, true).show()
    }
}

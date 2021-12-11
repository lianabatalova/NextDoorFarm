package tech.volkov.nextdoorfarm.frontend.fragment.customer.internal

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_customer_profile_edit.*
import kotlinx.android.synthetic.main.fragment_customer_profile_edit.view.*
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.CustomerAndOrdersDto
import tech.volkov.nextdoorfarm.backend.presenter.internal.CustomerProfileEditPresenter
import tech.volkov.nextdoorfarm.frontend.fragment.customer.CustomerProfileFragment
import tech.volkov.nextdoorfarm.frontend.view.internal.CustomerProfileEditView

class CustomerProfileEditFragment : MvpAppCompatFragment(), CustomerProfileEditView {

    private lateinit var mainActivity: AppCompatActivity

    @InjectPresenter
    lateinit var customerProfileEditPresenter: CustomerProfileEditPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_customer_profile_edit, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        mainActivity = activity as AppCompatActivity
        mainActivity.supportActionBar?.title =
            resources.getString(R.string.fragment_customer_profile_edit_title)
        setOnClickListener(view)

        customerProfileEditPresenter.getCustomer()
    }

    private fun setOnClickListener(view: View) {
        view.customerProfileEditSaveButton.setOnClickListener {
            val fullName = profileFullNameEdit.text.toString().split(" ")

            customerProfileEditPresenter.updateCustomer(
                firstName = fullName[0],
                lastName = fullName[1],
                address = customerProfileAddressEdit.text.toString(),
                username = customerProfileUsernameEdit.text.toString(),
                email = customerProfileEmailEdit.text.toString(),
                phone = customerProfilePhoneEdit.text.toString()
            )
        }

        view.customerProfileEditCancelButton.setOnClickListener {
            openCustomerProfileFragment()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun fillInUserInfo(user: CustomerAndOrdersDto) {
        profileFullNameEdit.setText("${user.firstName} ${user.lastName}")
        customerProfileUsernameEdit.setText(user.username)
        customerProfileAddressEdit.setText(user.address)
        customerProfileEmailEdit.setText(user.email)
        customerProfilePhoneEdit.setText(user.phone)
    }

    override fun showSuccessMessage(message: String) {
        Toasty.success(mainActivity, message, Toast.LENGTH_SHORT, true).show()
    }

    override fun openCustomerProfileFragment() {
        fragmentManager
            ?.beginTransaction()
            ?.replace(R.id.mainContainer, CustomerProfileFragment())
            ?.commit()
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(mainActivity, message, Toast.LENGTH_SHORT, true).show()
    }
}
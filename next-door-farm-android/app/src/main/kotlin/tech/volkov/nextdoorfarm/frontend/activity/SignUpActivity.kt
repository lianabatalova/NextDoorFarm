package tech.volkov.nextdoorfarm.frontend.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.UserLoggedInDto
import tech.volkov.nextdoorfarm.backend.model.UserType
import tech.volkov.nextdoorfarm.backend.presenter.SignUpPresenter
import tech.volkov.nextdoorfarm.frontend.view.LogInSignUpView

class SignUpActivity : MvpAppCompatActivity(), LogInSignUpView {

    @InjectPresenter
    lateinit var signUpPresenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        signUpSignUpButton.setOnClickListener {
            if (signUpPasswordText.text.toString() != signUpPasswordConfirmText.text.toString()) {
                showErrorMessage("Пароли не совпадают")
                return@setOnClickListener
            }

            val userType = when {
                signUpCustomerRadioButton.isSelected -> UserType.CUSTOMER
                signUpFarmerRadioButton.isSelected -> UserType.FARMER
                else -> UserType.CUSTOMER
            }

            signUpPresenter.signUpUser(
                signUpFirstNameText.text.toString(),
                signUpLastNameText.text.toString(),
                signUpUserNameText.text.toString(),
                signUpEmailText.text.toString(),
                signUpPasswordText.text.toString(),
                userType
            )
        }
    }

    override fun logInToApplication(user: UserLoggedInDto) {
        saveUserIdToSharedPreferences(user.id)
        startActivity(Intent(this, CustomerActivity::class.java))
    }

    private fun saveUserIdToSharedPreferences(userId: Long) {
        val sharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putLong(getString(R.string.user_id), userId).apply()
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
    }
}

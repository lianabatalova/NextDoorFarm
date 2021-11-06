package tech.volkov.nextdoorfarm.frontend.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_log_in.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.UserLoggedInDto
import tech.volkov.nextdoorfarm.backend.model.UserType
import tech.volkov.nextdoorfarm.backend.presenter.LogInPresenter
import tech.volkov.nextdoorfarm.frontend.view.LogInSignUpView

class LogInActivity : MvpAppCompatActivity(), LogInSignUpView {

    @InjectPresenter
    lateinit var loginPresenter: LogInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isUserLoggedIn()) {
            startMainActivity()
        } else {
            setContentView(R.layout.activity_log_in)
            init()
        }
    }

    private fun init() {
        logInLogInButton.setOnClickListener {
            val userType = when {
                logInCustomerRadioButton.isSelected -> UserType.CUSTOMER
                logInFarmerRadioButton.isSelected -> UserType.FARMER
                else -> UserType.CUSTOMER
            }

            loginPresenter.logInUserBy(
                logInLogInText.text.toString(),
                logInPasswordText.text.toString(),
                userType
            )
        }

        logInSignUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val userId = getSharedPreferences().getInt(getString(R.string.user_id), -1)
        return userId != -1
    }

    override fun logInToApplication(user: UserLoggedInDto) {
        val editor = getSharedPreferences().edit()
        editor.putLong(getString(R.string.user_id), user.id).apply()
        startMainActivity()
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
    }

    private fun getSharedPreferences(): SharedPreferences {
        val fileKey = getString(R.string.preference_file_key)
        return getSharedPreferences(fileKey, Context.MODE_PRIVATE)
    }

    private fun startMainActivity() = startActivity(Intent(this, MainActivity::class.java))
}

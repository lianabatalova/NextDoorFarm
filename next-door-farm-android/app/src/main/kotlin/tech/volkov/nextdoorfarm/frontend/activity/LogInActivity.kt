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
            logIn()
        } else {
            setContentView(R.layout.activity_log_in)
            init()
        }
    }

    private fun init() {
        logInLogInButton.setOnClickListener {
            val userType = when {
                logInLogInText.text.toString() == "kate" -> UserType.farmer
                else -> UserType.customer
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
        val userId = getSharedPreferences().getString(getString(R.string.user_id), "")
        return userId != ""
    }

    override fun logInToApplication(user: UserLoggedInDto) {
        val editor = getSharedPreferences().edit()
        editor.putString(getString(R.string.user_id), user.id).apply()
        editor.putString(getString(R.string.user_type), user.userType.toString()).apply()
        editor.putString(getString(R.string.token), user.token).apply()

        logIn()
    }

    private fun logIn() {
        val userType = getSharedPreferences()
            .getString(getString(R.string.user_type), "")
        startNextActivity(userType!!)
    }

    private fun startNextActivity(userType: String) {
        if (userType == UserType.customer.name) {
            startActivity(Intent(this, CustomerActivity::class.java))
        } else {
            startActivity(Intent(this, FarmerActivity::class.java))
        }
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
    }

    private fun getSharedPreferences(): SharedPreferences {
        val fileKey = getString(R.string.preference_file_key)
        return getSharedPreferences(fileKey, Context.MODE_PRIVATE)
    }
}

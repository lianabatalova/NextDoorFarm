package tech.volkov.nextdoorfarm.frontend.helper

class Validation {
    companion object {
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}

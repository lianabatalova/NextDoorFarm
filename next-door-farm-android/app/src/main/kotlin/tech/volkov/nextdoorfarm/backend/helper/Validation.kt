package tech.volkov.nextdoorfarm.backend.helper

class Validation {
    companion object {
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}

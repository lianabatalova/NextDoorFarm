package tech.volkov.nextdoorfarm.frontend.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatActivity
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.frontend.fragment.customer.CustomerOrdersFragment
import tech.volkov.nextdoorfarm.frontend.fragment.customer.CustomerProductsFragment
import tech.volkov.nextdoorfarm.frontend.fragment.customer.CustomerProfileFragment
import tech.volkov.nextdoorfarm.frontend.helper.BottomNavigationViewHelper

class CustomerActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(resources.getColor(R.color.white))

        bottomNavigationView.onNavigationItemSelectedListener = navListener
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView)

        val menu = bottomNavigationView.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true

        beginTransaction(CustomerProfileFragment())
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = CustomerProfileFragment()
            when (item.itemId) {
                R.id.nav_item_profile -> selectedFragment = CustomerProfileFragment()
                R.id.nav_item_products -> selectedFragment = CustomerProductsFragment()
                R.id.nav_item_orders -> selectedFragment = CustomerOrdersFragment()
            }
            beginTransaction(selectedFragment)
            true
        }

    private fun beginTransaction(selectedFragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.mainContainer, selectedFragment)
            .commit()

    companion object {
        private const val ACTIVITY_NUM = 2
    }
}

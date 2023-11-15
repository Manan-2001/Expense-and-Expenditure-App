package com.example.expensemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Switch
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.jetbrains.annotations.NotNull

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var frameLayout: FrameLayout

    private lateinit var dashBoardFragment: DashBoardFragment
    private lateinit var incomeFragment: IncomeFragment
    private lateinit var expenseFragment: ExpenseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar:Toolbar =findViewById(R.id.my_toolbar)
        toolbar.setTitle("Expense Manager")
        setSupportActionBar(toolbar)

        val drawerLayout:DrawerLayout=findViewById(R.id.drawer_layout)
        val toggle=ActionBarDrawerToggle( this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView:NavigationView=findViewById(R.id.navView)
        navigationView.setNavigationItemSelectedListener(this)

        dashBoardFragment= DashBoardFragment()
        incomeFragment= IncomeFragment()
        expenseFragment= ExpenseFragment()
        setFragment(dashBoardFragment)

        bottomNavigationView=findViewById(R.id.bottomNavigationbar)
        frameLayout=findViewById(R.id.main_frame)

        bottomNavigationView.setOnItemSelectedListener { item ->
            onNavigationItemSelected(item)
        }




        fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.dashboard -> {
                    setFragment(dashBoardFragment)
                    bottomNavigationView.setBackgroundResource(R.color.dashboard_color)
                    return true
                }
                R.id.income ->{
                    setFragment(incomeFragment)
                    bottomNavigationView.setBackgroundResource(R.color.income)
                    return true
                }
                R.id.expense ->{
                    setFragment(expenseFragment)
                    bottomNavigationView.setBackgroundResource(R.color.expense)
                    return true
                }
                else ->{
                    return false
                }

            }

        }



    }
    fun setFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame, fragment)
        fragmentTransaction.commit()
    }

    override  fun onBackPressed(){
        val drawerLayout:DrawerLayout=findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END)
        }else{
            super.onBackPressed()
        }


    }
        fun displaySelectedListner(itemId:Int){
            var fragment: Fragment? =null
            when (itemId){
                 R.id.dashboard ->{
                    fragment=DashBoardFragment()
                 }
                R.id.income ->{
                    fragment=IncomeFragment()
                }
                R.id.expense->{
                    fragment=ExpenseFragment()
                }
            }

            if (fragment!=null){
                val ft:FragmentTransaction=supportFragmentManager.beginTransaction()
                ft.replace(R.id.main_frame,fragment)
                ft.commit()
            }
            val drawerLayout:DrawerLayout=findViewById(R.id.drawer_layout)
            drawerLayout.closeDrawer(GravityCompat.START)

        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displaySelectedListner(item.itemId)
        return true
    }
}
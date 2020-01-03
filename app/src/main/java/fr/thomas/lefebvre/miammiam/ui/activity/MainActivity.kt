package fr.thomas.lefebvre.miammiam.ui.activity


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import fr.thomas.lefebvre.miammiam.R
import fr.thomas.lefebvre.miammiam.service.RecipeHelper
import fr.thomas.lefebvre.miammiam.ui.fragment.BookFragment
import fr.thomas.lefebvre.miammiam.ui.fragment.CategoryFragment
import fr.thomas.lefebvre.miammiam.ui.fragment.RecipesFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_ingredient.view.*
import kotlinx.android.synthetic.main.dialog_log_out.view.*
import kotlinx.android.synthetic.main.dialog_quitt.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val currentUser = FirebaseAuth.getInstance().currentUser
    val recipeHelper = RecipeHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        replaceFragment(RecipesFragment())

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        changeIconMenuDrawer()

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        updateUserInformations()

    }


    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_bottom_recipes -> {
                    replaceFragment(RecipesFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_bottom_category -> {
                    replaceFragment(CategoryFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_bottom_book -> {
                    replaceFragment(BookFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            alertDialogQuitt()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
//            R.id.action_search ->{
//
//            }
            R.id.action_add -> {
                startActivity(Intent(this, AddRecipeActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_profil -> {
                startActivity(Intent(this,AccountActivity::class.java))

            }
            R.id.nav_add_recipe -> {
                startActivity(Intent(this, AddRecipeActivity::class.java))
            }

            R.id.nav_log_out -> {

                alertDialogLogOut()
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun changeIconMenuDrawer() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_tomato)
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()

    }

    // --- GET USER INFORMATION ---

    fun updateUserInformations() {
        val navHeader = nav_view.getHeaderView(0)
        val navUserName = navHeader.textViewNameCurrentUser
        val stringBuilder = StringBuilder(getString(R.string.chef) + " " + currentUser?.displayName)
        navUserName.text = stringBuilder

    }

    // --- ALERT DIALOG LOG OUT ---

    private fun alertDialogLogOut() {
        val mDialog = LayoutInflater.from(this)
            .inflate(R.layout.dialog_log_out, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
        val mAlertDialog = mBuilder.show()
        mDialog.buttonValidLogOut.setOnClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            mAlertDialog.dismiss()

        }
        mDialog.buttonCancelLogOut.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

    private fun alertDialogQuitt() {
        val mDialog = LayoutInflater.from(this)
            .inflate(R.layout.dialog_quitt, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
        val mAlertDialog = mBuilder.show()
        mDialog.buttonValidQuitt.setOnClickListener {
           super.onBackPressed()
            mAlertDialog.dismiss()

        }
        mDialog.buttonCancelQuitt.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
}

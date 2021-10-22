package com.example.blooddonationapp.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.blooddonationapp.R
import com.example.blooddonationapp.viewModels.MainActivityViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private var mAppBarConfiguration: AppBarConfiguration? = null
    private val model: MainActivityViewModel by viewModels()
    var name: TextView? = null
    var email: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val view = navigationView.getHeaderView(0)
        name = view.findViewById(R.id.header_name)
        email = view.findViewById(R.id.header_email)
        email?.text = model.getUserEmail()
        model.userNameLiveData.observe(this, Observer {
            name?.text = it
        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data =
                Uri.parse("mailto:abdomahmoud20060@gmail.com") // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, model.getCurrentUser())
            intent.putExtra(Intent.EXTRA_TEXT, "Write The Problem Here")
            startActivity(intent)
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home,
            R.id.nav_Donate,
            R.id.nav_Request_Donation,
            R.id.nav_my_requests,
            R.id.nav_Settings,
            R.id.nav_About
        )
            .setDrawerLayout(drawer)
            .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.project, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        if (item.itemId == R.id.action_settings) {
            model.signOut()
            val `in` = Intent(applicationContext, LoginActivity::class.java)
            startActivity(`in`)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_home)

    }
}
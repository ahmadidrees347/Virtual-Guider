package io.mapwize.mapwizeuicomponents

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.mapbox.mapboxsdk.maps.MapboxMap
import io.indoorlocation.core.IndoorLocation
import io.indoorlocation.manual.ManualIndoorLocationProvider
import io.mapwize.mapwizecomponents.*
import io.mapwize.mapwizecomponents.ui.MapwizeFragment
import io.mapwize.mapwizecomponents.ui.MapwizeFragmentUISettings
import io.mapwize.mapwizecomponents.ui.UIBehaviour
import io.mapwize.mapwizeformapbox.api.MapwizeObject
import io.mapwize.mapwizeformapbox.api.Place
import io.mapwize.mapwizeformapbox.map.MapOptions
import io.mapwize.mapwizeformapbox.map.MapwizePlugin
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MapwizeFragment.OnFragmentInteractionListener, UIBehaviour {
    private var mapwizeFragment: MapwizeFragment? = null
    private var mapboxMap: MapboxMap? = null
    private var mapwizePlugin: MapwizePlugin? = null
    private var locationProvider: ManualIndoorLocationProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        setTitle("Show Availability Status")
        val availabilityStatus = AvailabilityStatus()
        supportFragmentManager.beginTransaction().replace(fragment.id, availabilityStatus).commit()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE)
        val status = sharedPreferences.getBoolean("Login", false)

        if(status) {
            val menu = navView.getMenu()
            menu.findItem(R.id.nav_signIn).setVisible(false)
            menu.findItem(R.id.nav_viewProfile).setVisible(true)
            menu.findItem(R.id.nav_editStatus).setVisible(true)
            menu.findItem(R.id.nav_signOut).setVisible(true)
        }
        else
        {
            val menu = navView.getMenu()
            menu.findItem(R.id.nav_signIn).setVisible(true)
            menu.findItem(R.id.nav_viewProfile).setVisible(false)
            menu.findItem(R.id.nav_editStatus).setVisible(false)
            menu.findItem(R.id.nav_signOut).setVisible(false)
        }

        navView.getMenu().getItem(0).setChecked(true);

        navView.setNavigationItemSelectedListener(this)
    }

    /**
     * Fragment listener
     */
    override fun onFragmentReady(mapboxMap: MapboxMap?, mapwizePlugin: MapwizePlugin?) {
        this.mapboxMap = mapboxMap
        this.mapwizePlugin = mapwizePlugin

        this.locationProvider = ManualIndoorLocationProvider()
        this.mapwizePlugin?.setLocationProvider(this.locationProvider!!)

        this.mapwizePlugin?.addOnLongClickListener {
            val indoorLocation = IndoorLocation("manual_provider", it.latLngFloor.latitude, it.latLngFloor.longitude, it.latLngFloor.floor, System.currentTimeMillis())
            this.locationProvider?.setIndoorLocation(indoorLocation)
        }
    }

    override fun onMenuButtonClick() {
        // TODO Do something with menu click
    }

    override fun onInformationButtonClick(place: Place?) {
        // TODO Do something with information button
    }

    /**
     * UIBehaviour
     * MapwizeFragment have a default UIBehaviour. You don't have to implement it if you do not need a custom behaviour.
     * This implementation is here for demo purpose.
     */
    override fun shouldDisplayInformationButton(mapwizeObject: MapwizeObject?): Boolean {
        when (mapwizeObject) {
            is Place -> return true
        }
        return false
    }

    override fun shouldDisplayFloorController(floors: MutableList<Double>?): Boolean {
        if (floors == null || floors.size <= 1) {
            return false
        }
        return true
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_ShowMap -> {
                setTitle("Show Map")
                // Uncomment and fill place holder to test MapwizeUI on your venue
                val opts = MapOptions.Builder()
                        //.restrictContentToOrganization("5c9f810f39021000166f452b")
                        .restrictContentToVenue("5ca21596e0841400515e8c98")
                        //.centerOnVenue("5ca21596e0841400515e8c98")
                        //.centerOnPlace("5ca640427dfb860016b0b50f")
                        .build()

                // Uncomment and change value to test different settings configuration
                var uiSettings = MapwizeFragmentUISettings.Builder()
                        .menuButtonHidden(false)
                        .followUserButtonHidden(false)
                        .floorControllerHidden(false)
                        .compassHidden(true)
                        .build()
                mapwizeFragment = MapwizeFragment.newInstance(opts, uiSettings)
                this.mapwizeFragment?.uiBehaviour = this
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(fragment.id, mapwizeFragment!!)
                ft.commit()
            }
            R.id.nav_ViewSchedule -> {
                setTitle("View Schedule")
                val viewSchedule = ViewSchedule()
                supportFragmentManager.beginTransaction().replace(fragment.id, viewSchedule).commit()
            }
            R.id.nav_ViewTimetable -> {
                setTitle("View Timetable")
                val viewTimetable = ViewTimetable()
                supportFragmentManager.beginTransaction().replace(fragment.id, viewTimetable).commit()
            }
            R.id.nav_AvailabilityStatus -> {
                setTitle("Show Availability Status")
                val availabilityStatus = AvailabilityStatus()
                supportFragmentManager.beginTransaction().replace(fragment.id, availabilityStatus).commit()
            }
            R.id.nav_viewProfile -> {
                setTitle("Faculty Profile")
                val facultyProfile = FacultyProfile()
                supportFragmentManager.beginTransaction().replace(fragment.id, facultyProfile).commit()
            }
            R.id.nav_editStatus -> {
                setTitle("Edit Availability Status")
                val editFacultyStatus = EditFacultyStatus()
                supportFragmentManager.beginTransaction().replace(fragment.id, editFacultyStatus).commit()
            }
            R.id.nav_signOut -> {
                val sp = getSharedPreferences("status" , Context.MODE_PRIVATE)
                sp.edit().putBoolean("Login",false).commit()
                this.recreate()
            }
            R.id.nav_signIn -> {
                setTitle("signIn")
                val signinFragment  = signinFragment()
                supportFragmentManager.beginTransaction().replace(fragment.id, signinFragment ).commit()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}

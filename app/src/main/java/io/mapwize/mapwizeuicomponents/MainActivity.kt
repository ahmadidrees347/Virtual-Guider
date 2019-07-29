package io.mapwize.mapwizeuicomponents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mapbox.mapboxsdk.maps.MapboxMap
import io.indoorlocation.core.IndoorLocation
import io.indoorlocation.manual.ManualIndoorLocationProvider
import io.mapwize.mapwizecomponents.ui.MapwizeFragment
import io.mapwize.mapwizecomponents.ui.MapwizeFragmentUISettings
import io.mapwize.mapwizecomponents.ui.UIBehaviour
import io.mapwize.mapwizeformapbox.api.MapwizeObject
import io.mapwize.mapwizeformapbox.api.Place
import io.mapwize.mapwizeformapbox.map.MapOptions
import io.mapwize.mapwizeformapbox.map.MapwizePlugin
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MapwizeFragment.OnFragmentInteractionListener, UIBehaviour {


    private var mapwizeFragment: MapwizeFragment? = null
    private var mapboxMap: MapboxMap? = null
    private var mapwizePlugin: MapwizePlugin? = null
    private var locationProvider: ManualIndoorLocationProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        ft.add(fragmentContainer.id, mapwizeFragment!!)
        ft.commit()

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

}

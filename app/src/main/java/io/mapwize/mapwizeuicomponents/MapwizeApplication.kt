package io.mapwize.mapwizeuicomponents


import android.app.Application
import io.mapwize.mapwizeformapbox.AccountManager

class MapwizeApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AccountManager.start(this, "77f6176cf375a7869d501603e2538482")
    }

}
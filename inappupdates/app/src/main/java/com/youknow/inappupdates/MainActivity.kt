package com.youknow.inappupdates

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }
    private lateinit var listener: InstallStateUpdatedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkUpdate()
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener {
                Log.d(TAG, "[WT][in-app updates] InstallState: ${it.installStatus().installStatusName()}")
                if (it.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            }
    }

    private fun checkUpdate() {
        listener = InstallStateUpdatedListener {
            Log.d(TAG, "[WT][in-app updates] InstallState: ${it.installStatus().installStatusName()}")
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate()
                appUpdateManager.unregisterListener(listener)
            }
        }
        appUpdateManager.registerListener(listener)
        appUpdateManager.appUpdateInfo.addOnCompleteListener {
            val appUpdateInfo = it.result
            Log.d(TAG, "[WT][in-app updates] updateAvailability: ${appUpdateInfo.updateAvailability().updateAvailabilityName()}")

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                Log.d(TAG, "[WT][in-app updates] should show update")
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    1
                )
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.activity_chooser_view_content),
            "An update has just been downloaded.", Snackbar.LENGTH_INDEFINITE
        )
            .setAction("UPDATE") { appUpdateManager.completeUpdate() }
            .show()
    }

}

private fun Int.updateAvailabilityName() = when (this) {
    1 -> "UPDATE_NOT_AVAILABLE"
    2 -> "UPDATE_AVAILABLE"
    3 -> "DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS"
    else -> "UNKNOWN"
}

private fun Int.installStatusName() = when (this) {
    10 -> "REQUIRES_UI_INTENT"
    1 -> "PENDING"
    2 -> "DOWNLOADING"
    11 -> "DOWNLOADED"
    3 -> "INSTALLING"
    4 -> "INSTALLED"
    5 -> "FAILED"
    6 -> "CANCELED"
    else -> "UNKNOWN"
}


package com.example.weatherapp

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PowerManager
import android.os.SystemClock
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.WorkManager
import com.example.weatherapp.camera.CameraCapture
import com.example.weatherapp.data.alarms.AlarmRepository
import com.example.weatherapp.data.hardware.SavedHardwareRepository
import com.example.weatherapp.data.preferences.UserPreferencesRepository
import com.example.weatherapp.ui.theme.WeatherAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : ComponentActivity() {

    private lateinit var alarmManager : AlarmManager
    private lateinit var viewModel: MainViewModel
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // temporary solution
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        // possible better solution, requires api level 27, maybe do for 27 >= and < 27
        //setTurnScreenOn(true)
        requestPermissions()
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
        alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val powerManager = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
        val repo = UserPreferencesRepository(this)
        val hwRepo = SavedHardwareRepository(this)
        val alarmRepo = AlarmRepository(this)
        val stationClient = StationClient()
        viewModel = ViewModelProvider(
            this,
            MainViewModel.Companion.MainViewModelFactory(
                repo, hwRepo, alarmRepo, powerManager, stationClient
            )
        ).get(MainViewModel::class.java)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainWindow(viewModel)
                }
            }
        }
    }

    private fun requestPermissions(){
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(
                    this,
                    "Permission granted!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Permission not granted by user.",
                    Toast.LENGTH_LONG
                    ).show()
                finish()
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }

}

@Composable
fun MainWindow(
    viewModel: MainViewModel
){
    val cameraOn by viewModel.cameraOn.collectAsState()
    val localContext = LocalContext.current
    if(cameraOn){
        CameraCapture(
            onImageFile = { Log.d("CAMERA", "Photo: $it")
                            Toast.makeText(
                                localContext,
                                "Image captured!",
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.onPhotoTaken(it)
                          },
            onException = {
                Log.e("CAMERA", "Error", it.cause)
                Toast.makeText(
                    localContext,
                    "Error! Image not captured!",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.onCameraError(it)
            }
        )
    } else {
        MainPage(viewModel = viewModel)
    }
}


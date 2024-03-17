package com.example.transitionapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.transition.Explode
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Slide
import com.example.transitionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            exitTransition= android.transition.Slide(Gravity.LEFT)
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getPermission()
        binding.transitionButton.setOnClickListener{
            //Starts Service
            startService(Intent(this@MainActivity,BackGroundService::class.java))

            //Move activity to the background
            moveTaskToBack(true)
        }
    }

    //For getting permission from settings
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getPermission(){
        if(!Settings.canDrawOverlays(this)){
            val  i=Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(i,1)
        }
    }

    //Checking for permission granted od denied
    @RequiresApi(Build.VERSION_CODES.M)
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            if(!Settings.canDrawOverlays(this)){
                Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
        }
}
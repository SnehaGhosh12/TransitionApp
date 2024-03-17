package com.example.transitionapp

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.annotation.RequiresApi


class BackGroundService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    var view: View?=null
    var windowManager: WindowManager?=null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var layout_flag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        var layoutParams = WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
            750,
            layout_flag,WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,PixelFormat.TRANSLUCENT)

        view =LayoutInflater.from(this).inflate(R.layout.bottomsheet,null)
        layoutParams.gravity=Gravity.BOTTOM
        windowManager =getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager!!.addView(view,layoutParams)

        //Closing Service
        view!!.findViewById<ImageView>(R.id.clear).setOnClickListener{
            stopSelf()
        }

        //Moving back to App on layout clicked
        view!!.findViewById<View>(R.id.layout).setOnClickListener {
            var intent =Intent(applicationContext,MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            stopSelf()
            startActivity(intent)
        }




        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (view != null) {
            windowManager!!.removeView(view)
        }
    }



}
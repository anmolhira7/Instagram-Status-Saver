package com.goup.anmolhira

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import papayacoders.instastory.Stories
import papayacoders.instastory.Stories.login
import papayacoders.instastory.models.TrayModel
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), UserInterface {

    val permissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting up recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.userProfileRecyclerView)
        val storyRecyclerView = findViewById<RecyclerView>(R.id.story_recyclerview)


        val profileAdapter = ProfileAdapter(this)
        recyclerView.adapter = profileAdapter

        //setting up story adapter
        val storyAdapter = StoryAdapter()
        storyRecyclerView.adapter = storyAdapter

        checkPermission()

        val button = findViewById<Button>(R.id.login)
        button.setOnClickListener {
            Stories.login(this)
        }

        Stories.users(this)

        // Stories.getStories(this, "1373600159")
        Stories.storyList.observe(this, {
            storyAdapter.submitList(it)
            for (item in it) {
                Log.d("Specific User Story", "name: ${item.imageversions2.candidates}")
            }
        })


        Stories.list.observe(this, {
            profileAdapter.submitList(it)
            for (item in it) {
                Log.d("MainActivity", "name: ${item.user.fullname}")
            }
        })
    }

    private fun checkPermission(): Boolean {
        var result: Int
        val listPermissionNeeded: MutableList<String> = ArrayList()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)

            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionNeeded.add(p)
            }
        }

        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toTypedArray(), 0)
            return false
        }
        return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0){
            if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this,"Please Accept All permissions",Toast.LENGTH_SHORT).show()
            }
            checkPermission()
        }
    }

    override fun userInterfaceClick(i: Int, traymodel: TrayModel) {
        Stories.getStories(this, traymodel.user.pk.toString())
    }

    /*Boolean - Checks whether user is logged in or not */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Stories.isLogin(this)) {
            Stories.users(this)
        }
    }
}
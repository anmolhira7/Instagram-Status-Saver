package com.goup.anmolhira.util

import android.os.Environment
import java.io.File

object DirectoryUtils {

    val DIRECTORY = "/Insta Saver/"
    val DIRECTORY_FOLDER = File("${Environment.getExternalStorageDirectory()}/Download/$DIRECTORY")

    fun createFile(){
        if(!DIRECTORY_FOLDER.exists()){
            DIRECTORY_FOLDER.mkdirs()
        }
    }
}
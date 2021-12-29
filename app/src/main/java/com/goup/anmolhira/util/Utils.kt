package com.goup.anmolhira.util

import android.app.DownloadManager
import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.widget.Toast
import java.io.File

object Utils {

    fun startDownload(
        downloadPath: String,
        destinationPath: String,
        context: Context,
        fileName: String
    ) {
        Toast.makeText(context, "Downloading started", Toast.LENGTH_SHORT).show()
        val request = DownloadManager.Request(Uri.parse(downloadPath))

        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_MOBILE
                    or DownloadManager.Request.NETWORK_WIFI
        )
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle(fileName)
        request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, destinationPath + fileName)
        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
        MediaScannerConnection.scanFile(
            context,
            arrayOf(File(DIRECTORY_DOWNLOADS + "/" + destinationPath + fileName).absolutePath),
            null
        ) { _, _ -> }

    }


}
package com.example.movie.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {


    companion object {
        fun isNetworkConnected(context: Context):Boolean{
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            return nInfo != null && nInfo.isAvailable && nInfo.isConnected
        }
    }

}
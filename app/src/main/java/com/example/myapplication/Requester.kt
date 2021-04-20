package com.example.parser3

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import java.lang.Exception
import java.net.URL

private fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }

            }
        }
    }

    return result
}

class Requester {
    private var url : String = "https://api.github.com/search/repositories?q="
    private var q: String

    constructor(q  : String = "uni-math-2.2-geometric_probability"){
        this.q =q
    }

    fun updateQ(q :String){
        if (q.isEmpty())
            this.q="uni-math-2.2-geometric_probability"
        else
            this.q = q
    }

    fun run(context: Context) : QueryResult {
        lateinit var reposJsonStr : String

        if(!isInternetAvailable(context)){
            throw Exception("Internet connectivity(<- joke) problem")
        }

        //Yeap, thanks to the GSON and this, that's all what is needed for a simple json query
        val thread : Thread = Thread(Runnable{
            reposJsonStr = URL(url+q).readText()
        })
        thread.start()
        thread.join()
        return Gson().fromJson(reposJsonStr, QueryResult::class.java)
    }
}
package com.graphicless.cricketapp.network

import android.content.Context
import android.net.*

interface NetworkConnectivityCallback {
    fun onNetworkLost()
    fun onNetworkAvailable()
}

class NetworkConnectivityChecker(private val context: Context) {

    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var callback: NetworkConnectivityCallback? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // Network is available
            callback?.onNetworkAvailable()
        }

        override fun onLost(network: Network) {
            // Network is lost
            callback?.onNetworkLost()
        }
    }

    fun setCallback(callback: NetworkConnectivityCallback) {
        this.callback = callback
    }

    fun start() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
    fun addOnConnectivityChangedListener(listener: NetworkConnectivityCallback) {
        callback = listener
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            listener.onNetworkAvailable()
        }
    }

}

/*

class NetworkConnectivityChecker(context: Context) : LifecycleObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var isConnected: Boolean = false

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            if (!isConnected) {
                showNotification(MyApplication.instance, "Internet connected", "You are now connected to the internet.")
                isConnected = true
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            if (isConnected) {
                showNotification(MyApplication.instance, "No internet connection", "You are not connected to the internet.")
                isConnected = false
            }
        }
    }
    val networkRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        // Use the new API for devices running Android N and higher
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
    } else {
        // Use the legacy API for devices running Android M and lower
        NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .build()
    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    companion object {
        private const val CHANNEL_ID = "network_notification_channel"
        private const val NOTIFICATION_ID = 1

        private fun showNotification(context: Context, title: String, message: String) {
            createNotificationChannel(context)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_back)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        MyApplication.instance,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notify(NOTIFICATION_ID, builder.build())
            }
        }

        private fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "Network Notification Channel"
                val descriptionText = "Notification for network connectivity status"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}
*/

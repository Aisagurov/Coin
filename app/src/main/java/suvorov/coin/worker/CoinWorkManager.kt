package suvorov.coin.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import suvorov.coin.R
import suvorov.coin.data.remote.model.CurrencyApi
import suvorov.coin.ui.MainActivity
import suvorov.coin.util.priceDollarString
import java.net.URL

class CoinWorkManager(
    context: Context, parameters: WorkerParameters
): CoroutineWorker(context, parameters) {

    companion object {
        const val CHANNEL_ID = "Channel Id"
        const val BITCOIN_URL = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=bitcoin"
        const val ETHEREUM_URL = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=ethereum"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val bitcoinData = URL(BITCOIN_URL).readText()
        val ethereumData = URL(ETHEREUM_URL).readText()
        val bitcoin = jsonToObject(bitcoinData)
        val ethereum = jsonToObject(ethereumData)
        return@withContext try {
            createNotificationChannel()
            pendingNotification(
                bitcoin.first().name ?: "",
                bitcoin.first().price ?: 0.0,
                1
            )
            pendingNotification(
                ethereum.first().name ?: "",
                ethereum.first().price ?: 0.0,
                2
            )
            Result.success()
        }catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Channel description"
            }
            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun pendingNotification(name: String, price: Double, notifyId: Int) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pendingIntent = PendingIntent.getActivity(
                applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
            val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(name)
                .setContentText(price.priceDollarString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(notifyId, notification.build())
            }
        }
    }

    private fun jsonToObject(json: String): List<CurrencyApi> {
        return Gson().fromJson(json, object: TypeToken<List<CurrencyApi>>(){}.type)
    }
}
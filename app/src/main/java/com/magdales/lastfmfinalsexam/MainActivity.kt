package com.magdales.lastfmfinalsexam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val searchEntry: String? = null
    private var field: EditText? = null


    private val API_KEY = "5bcca0696b69e6c62977cff0ce97372f"
    private val API_SECRET = "b794a2f582e36e74835e3a4e9d7d302d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        field = findViewById<EditText>(R.id.editText_search)
    }

    override fun onResume() {
        super.onResume()

        field?.setOnEditorActionListener( { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                progressBar.visibility = View.VISIBLE

                val url = "http://ws.audioscrobbler.com/2.0/" + searchEntry
                val request = okhttp3.Request.Builder().url(url).build()
                val client = OkHttpClient.Builder().build()

                client.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Toast.makeText(this@MainActivity, "Request failed.", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if(response != null && response.isSuccessful) {
                            val jsonObj = response.body()?.string()
                            getData(jsonObj)
                        }
                    }
                })
                handled = true
            }
        handled
        })
    }

    private fun getData(jsonObj: String?){
        runOnUiThread {
            val length = JSONObject(jsonObj).getJSONArray("album").length()
            val albumlist = ArrayList<Album>()
            var pos = 0
            for (i in 1..length) {
                val albumName = JSONObject(jsonObj).getJSONArray("album").getJSONObject(pos).getJSONObject("name")
                val artistName = JSONObject(jsonObj).getJSONArray("album").getJSONObject(pos).getJSONObject("artist")
                albumlist.add(Album(albumName,artistName))
                val adapter = AlbumAdapter(albumlist)
                pos++
            }
        }
    }
}

package com.magdales.lastfmfinalsexam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_layout.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener



class MainActivity : AppCompatActivity() {

    private var searchEntry: String? = null

    private val API_KEY = "5bcca0696b69e6c62977cff0ce97372f"
    private val API_SECRET = "b794a2f582e36e74835e3a4e9d7d302d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        editText_search?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchEntry = editText_search.text.toString()
                    progressBar.visibility = View.VISIBLE
                    lbl_no_album_present.visibility = View.INVISIBLE

                    val url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + searchEntry + "&api_key=" + API_KEY + "&format=json"
                    val request = okhttp3.Request.Builder().url(url).build()
                    val client = OkHttpClient.Builder().build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call?, e: IOException?) {
//                            Toast.makeText(this@MainActivity, "Request failed", Toast.LENGTH_SHORT).show()
//                            progressBar.visibility = View.GONE
                        }

                        override fun onResponse(call: Call?, response: Response?) {
                            if (response != null && response.isSuccessful) {
                                val jsonObj = response.body()?.string()
                                getData(jsonObj)
                            }
                        }
                    })
                    handled = true
                }
                return handled
            }
        })
    }

    private fun getData(jsonObj: String?){
        runOnUiThread {
            progressBar.visibility = View.GONE
            val gsonObj = GsonBuilder().create()
            val album = gsonObj.fromJson(jsonObj, Album::class.java)

            if (searchEntry == album.name) {
                textView_name.text = album.name
                textView_artist.text = album.artist
                Picasso.with(this@MainActivity).load(album.image).into(imageView)
                progressBar.visibility = View.GONE
            }

            val length = JSONObject(jsonObj).getJSONArray("album").length()
            val albumlist = ArrayList<Album>()
            var pos = 0
            for (i in 1..length) {
                val albumName = JSONObject(jsonObj).getJSONArray("album").getJSONObject(pos).getString("name").toString()
                val artistName = JSONObject(jsonObj).getJSONArray("album").getJSONObject(pos).getString("artist").toString()
                val albumImage = JSONObject(jsonObj).getJSONArray("album").getJSONObject(pos).getJSONObject("image").getString("#text").toString()
                albumlist.add(Album(albumName, artistName, albumImage))
                val adapter = AlbumAdapter(albumlist)
                recyclerView.adapter = adapter
                pos++
            }
        }
    }
}

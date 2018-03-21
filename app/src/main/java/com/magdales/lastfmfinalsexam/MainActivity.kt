package com.magdales.lastfmfinalsexam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL


class MainActivity : AppCompatActivity() {

    private var searchEntry: String? = null
    private val albumlist = ArrayList<Album>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        editText_search?.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false

                searchEntry = editText_search.text.toString()
                val url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=" + searchEntry + "&api_key=5bcca0696b69e6c62977cff0ce97372f&format=json"

                progressBar.visibility = View.VISIBLE
                lbl_no_album_present.visibility = View.GONE

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    for (i in 0..49) {
                        doAsync {
                            val resultJson = URL(url).readText()
                            val jsonObject = JSONObject(resultJson)

                            val name = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getString("name")
                            val artist = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getString("artist")
                            var image = if (jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text") == "") {
                                jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("")
                            } else {
                                jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")
                            }

                            if (image == "") {
                                image = "null"
                            }

                            uiThread {
                                albumlist.add(Album(name, artist, image))
                                recyclerView.adapter = AlbumAdapter(this@MainActivity, albumlist)
                            }
                        }
                    }
                    handled = true
                }
                return handled
            }
        })
    }
}

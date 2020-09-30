package com.example.meme_share

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    var currenturl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme(){
// Instantiate the RequestQueue.
        progressBar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url1 = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(Request.Method.GET, url1,null,
                { response ->
   currenturl =response.getString("url")
                    Glide.with(this).load(currenturl).listener(object :RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility= View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility= View.GONE
                            return false
                        }
                    }).into(memeimage)

                },
                {
Toast.makeText(this,"NOT CALLED",Toast.LENGTH_SHORT).show()
                })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun nextcall(view: View) {
        loadmeme()
    }

    fun shareit(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"$currenturl")
        val chooser = Intent.createChooser(intent,"sharing to via url")
       startActivity(chooser)
    }
}
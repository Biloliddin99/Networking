package com.example.networking

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.networking.adapters.UserAdapter
import com.example.networking.databinding.ActivityMainBinding
import com.example.networking.models.User
import com.example.networking.utils.MyWebSocketClient
import com.example.networking.utils.NetworkHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.net.URI

class MainActivity : AppCompatActivity() {
    private lateinit var requestQueue:RequestQueue
    lateinit var userAdapter: UserAdapter
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*
        myNetworkHelper = NetworkHelper(this)
        val tv = findViewById<TextView>(R.id.tv_net)
        tv.setOnClickListener {
            if (myNetworkHelper.isNetworkConnected()){
                tv.text = "Connected"
            }else{
                tv.text = "Not connected"
            }
        }
        getImage()
        val uri = URI("ws://your.websocket.server.url")
        val client = MyWebSocketClient(uri)
        client.connect()
        val message = "Hello, server!"
        client.send(message)
*/
        requestQueue = Volley.newRequestQueue(this)
        getArrayJson()

    }
    /*
        private fun getImage(){
            val imageLink = "https://i.imgur.com/Nwk25LA.jpg"
            val imageRequest = ImageRequest(imageLink,
                object :Response.Listener<Bitmap>{
                    override fun onResponse(response: Bitmap?) {
                        findViewById<ImageView>(R.id.tv_net)
                            .setImageBitmap(response)
                    }
                },
            0,0,ImageView.ScaleType.CENTER_CROP,
            Bitmap.Config.ARGB_8888,
                object :Response.ErrorListener{
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
                )
            requestQueue.add(imageRequest)
        }
    */


    private fun getArrayJson(){
        val link = "https://api.github.com/users"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, link, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {

                    val type = object : TypeToken<List<User>>(){}.type
                    val list = Gson().fromJson<List<User>>(response.toString(), type)

                    userAdapter = UserAdapter(list)
                    binding.rv.adapter = userAdapter

//                    findViewById<TextView>(R.id.tv_net)
//                        .text = response.toString()
                    Log.d(TAG, "onResponse : ${response.toString()}")
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        jsonArrayRequest.tag = "tag1"
        requestQueue.add(jsonArrayRequest)
        requestQueue.cancelAll("tag1")
    }


}
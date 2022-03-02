package com.example.practicaexamen


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practicaexamen.databinding.SecondActivityBinding
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class SecondActivity: AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    companion object {
        const val url = "url"

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val initialData = intent.getStringExtra(url)
        binding.button.setOnClickListener() {

            val client = OkHttpClient()

            val request = Request.Builder()
            request.url(initialData.toString())


            val call = client.newCall(request.build())
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@SecondActivity, "Algo ha ido mal", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    println(response.toString())
                    response.body?.let { responseBody ->
                        val body = responseBody.string()
                        println(body)
                        val gson = Gson()

                        val planeta = gson.fromJson(body, Result::class.java)
                        CoroutineScope(Dispatchers.Main).launch {
                            binding.textView.text = planeta.name
                            binding.textView2.text = planeta.population
                            binding.textView3.text = planeta.climate
                            Toast.makeText(
                                this@SecondActivity,
                                planeta.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })

        }
    }
}
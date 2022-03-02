package com.example.practicaexamen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class MainActivityViewModel : ViewModel() {

    var planetResponse = PlanetResponse(mutableListOf())


    private val _responsePlanet by lazy { MediatorLiveData<PlanetResponse>() }
    val responsePlanet: LiveData<PlanetResponse>
        get() = _responsePlanet

    suspend fun setResponsePlanetInMainThread(planetResponse: PlanetResponse) =
        withContext(Dispatchers.Main) {
            _responsePlanet.value = planetResponse
        }

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText: LiveData<String>
        get() = _responseText

    suspend fun setResponseTextInMainThread(value: String) = withContext(Dispatchers.Main) {
        _responseText.value = value
    }


    fun downloadInfo() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {

                val client = OkHttpClient()
                val request = Request.Builder()
                request.url("https://swapi.dev/api/planets/")

                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        CoroutineScope(Dispatchers.Main).launch {
                            setResponseTextInMainThread("Algo ha ido mal")
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->

                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()
                            planetResponse = gson.fromJson(body, PlanetResponse::class.java)
                            println("----------------------------- "+planetResponse.results)

                            CoroutineScope(Dispatchers.Main).launch {
                                setResponsePlanetInMainThread(planetResponse)
                            }
                        }
                    }
                })
            }
        }
    }

}
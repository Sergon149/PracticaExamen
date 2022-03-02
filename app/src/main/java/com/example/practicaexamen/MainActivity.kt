package com.example.practicaexamen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practicaexamen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TextoAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.downloadInfo()

        initObserver()
    }

    fun initObserver(){
        viewModel.responsePlanet.observe(this){
            adapter = TextoAdapter(it.results)
            println("--------------------------------   "+adapter.listaPlanetas[0].name+" - "+adapter.listaPlanetas[0].name)
            binding.recyclerView.adapter=adapter
        }
    }
}
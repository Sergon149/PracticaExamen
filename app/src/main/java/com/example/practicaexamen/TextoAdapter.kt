package com.example.practicaexamen

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practicaexamen.databinding.ItemPlanetaBinding

class TextoAdapter(var listaPlanetas : List<Result>) : RecyclerView.Adapter <TextoAdapter.TextoViewHolder>(){


    class TextoViewHolder(var itemPlanetaBinding: ItemPlanetaBinding):RecyclerView.ViewHolder(itemPlanetaBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        println("--------------------------------HE LLEGADO------------------------------------")
        val binding = ItemPlanetaBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        println("--------------------------------HE LLEGADO------------------------------------"+listaPlanetas[position].name)

        holder.itemPlanetaBinding.nombrePlaneta.text = listaPlanetas[position].name

        holder.itemPlanetaBinding.planet.setOnClickListener {
            val intent = Intent (holder.itemPlanetaBinding.root.context, SecondActivity::class.java)
            intent.putExtra("url",listaPlanetas[position].url)
            holder.itemPlanetaBinding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaPlanetas.size
    }
}
package com.example.pokeapi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokeapi.data.model.PokemonItem
import com.example.pokeapi.databinding.ItemPokemonBinding


class PokemonAdapter(private val onClick: (PokemonItem) -> Unit) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    private val items = mutableListOf<PokemonItem>()

    fun submitList(list: List<PokemonItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonItem) {
            val displayName = item.name.replaceFirstChar { it.uppercase() }
            binding.tvName.text = displayName

            // Extract ID from URL like .../pokemon/{id}/
            val id = item.url.trimEnd('/').split("/").lastOrNull() ?: "0"
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
            binding.ivPokemon.load(imageUrl) {
                crossfade(true)
            }

            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

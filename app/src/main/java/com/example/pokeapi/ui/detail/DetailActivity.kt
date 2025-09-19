package com.example.pokeapi.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.pokeapi.databinding.ActivityDetailBinding
import com.example.pokeapi.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Details"

        binding.detailToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val name = intent.getStringExtra("name") ?: run {
            finish()
            return
        }

        viewModel.pokemonDetail.observe(this) {
            when (it) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val pokemon = it.data
                    binding.tvName.text = pokemon?.name?.replaceFirstChar { c -> c.uppercase() }
                    val imageUrl = pokemon?.sprites?.frontDefault
                        ?: pokemon?.sprites?.other?.officialArtwork?.frontDefault
                    binding.ivPokemon.load(imageUrl) { crossfade(true) }

                    val statsText = pokemon?.stats?.joinToString("\n") {
                        "${it.stat.name.replaceFirstChar { c -> c.uppercase() }}: ${it.baseStat}"
                    } ?: "No stats"
                    binding.tvStats.text = statsText

                    // types
                    val types = pokemon?.types?.joinToString(", ") { it.type.name.replaceFirstChar { c -> c.uppercase() } }
                    binding.tvTypes.text = "Type: ${types ?: "N/A"}"

                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.fetchPokemonDetail(name)
    }
}

package com.example.pokeapi.data.repository

import com.example.pokeapi.data.remote.PokeApi
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val api: PokeApi) {
    suspend fun getPokemonList() = api.getPokemonList()
    suspend fun getPokemonDetail(name: String) = api.getPokemonDetail(name)
}
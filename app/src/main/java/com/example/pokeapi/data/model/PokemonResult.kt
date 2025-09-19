package com.example.pokeapi.data.model

data class PokemonResult(
    val name: String,
    val url: String
) {
    fun getId(): String {
        return url.trimEnd('/').split("/").last()
    }
}

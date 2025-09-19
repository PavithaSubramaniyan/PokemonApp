package com.example.pokeapi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.data.model.PokemonItem
import com.example.pokeapi.data.model.PokemonResult
import com.example.pokeapi.data.repository.PokemonRepository
import com.example.pokeapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: PokemonRepository) : ViewModel() {

    private val _pokemonList = MutableLiveData<Resource<List<PokemonItem>>>()
    val pokemonList: LiveData<Resource<List<PokemonItem>>> = _pokemonList

    fun fetchPokemonList() {
        viewModelScope.launch {
            _pokemonList.value = Resource.Loading()
            try {
                val response = repo.getPokemonList()
                _pokemonList.value = Resource.Success(response.results)
            } catch (e: Exception) {
                _pokemonList.value = Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }
}

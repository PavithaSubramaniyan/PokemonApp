package com.example.pokeapi.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapi.data.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.*
import com.example.pokeapi.data.remote.Root
import com.example.pokeapi.utils.Resource


@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: PokemonRepository) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<Resource<Root>>()
    val pokemonDetail: LiveData<Resource<Root>> = _pokemonDetail

    fun fetchPokemonDetail(name: String) {
        viewModelScope.launch {
            _pokemonDetail.value = Resource.Loading()
            try {
                val response = repo.getPokemonDetail(name)
                _pokemonDetail.value = Resource.Success(response)
            } catch (e: Exception) {
                _pokemonDetail.value = Resource.Error(e.message ?: "Unknown Error")
            }
        }
    }
}

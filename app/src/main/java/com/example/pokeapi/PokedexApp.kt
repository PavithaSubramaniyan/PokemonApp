package com.example.pokeapi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokedexApp : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}
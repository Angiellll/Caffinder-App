package com.example.first_test

object CafeDataStore {
    private var cafes: List<Cafe> = emptyList()

    fun setCafes(newCafes: List<Cafe>) {
        cafes = newCafes
    }

    fun getAllCafes(): List<Cafe> = cafes
}
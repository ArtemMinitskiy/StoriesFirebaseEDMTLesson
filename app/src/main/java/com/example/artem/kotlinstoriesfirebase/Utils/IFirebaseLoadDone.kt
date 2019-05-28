package com.example.artem.kotlinstoriesfirebase.Utils

interface IFirebaseLoadDone {
    fun onFirebaseLoadSuccess(movieList: List<Movie>)
    fun onFirebaseLoadFailed(message: String)
}
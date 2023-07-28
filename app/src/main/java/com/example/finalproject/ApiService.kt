package com.example.finalproject

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("floods?admin=ID-JK") // Ganti dengan endpoint API
    fun getBencana(): Call<List<Banjir>> // Menggunakan Call<List<Bencana>> karena kita mengharapkan data berupa List<Bencana> dari API
}
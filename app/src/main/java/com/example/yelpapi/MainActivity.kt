package com.example.yelpapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "gubZoGk1Snwg-wxf6EVQMxbcJBehzy_UbanPgNp-Aois43Dn9ELsVeio6XEaBbBlnYVwS3yjS2jGzAG7Epoq6OSvR5NlwKU8tZASSEseanE39f8YaxaVpMPO1ophYnYx"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retroFit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retroFit.create(YelpService::class.java)
        yelpService.searchResturants("Bearer $API_KEY","pizza", "New Britain").enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
               Log.i(TAG, "onResponse $response")
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }


        } )
    }
}
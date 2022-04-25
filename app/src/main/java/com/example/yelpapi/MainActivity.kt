package com.example.yelpapi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "gubZoGk1Snwg-wxf6EVQMxbcJBehzy_UbanPgNp-Aois43Dn9ELsVeio6XEaBbBlnYVwS3yjS2jGzAG7Epoq6OSvR5NlwKU8tZASSEseanE39f8YaxaVpMPO1ophYnYx"
val retroFit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

val yelpService = retroFit.create(YelpService::class.java)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun search (view: View){

        val resturants = mutableListOf<YelpResturant>()
        val adapter = ResturantsAdapter(this,resturants)
        Resturants.adapter = adapter
        Resturants.layoutManager = LinearLayoutManager(this)
        resturants.clear()

        val food = food_search.text.toString()
        val location = city_search.text.toString()

        val foodSearchBar = findViewById<EditText>(R.id.food_search)
        val citySearchBar = findViewById<EditText>(R.id.city_search)

        if(food.isNotEmpty() || location.isNotEmpty()){

            yelpService.searchResturants("Bearer $API_KEY", food, location)
                .enqueue(object : Callback<YelpSearch> {
                    override fun onResponse(
                        call: Call<YelpSearch>,
                        response: Response<YelpSearch>
                    ) {
                        val body = response.body()
                        if (body == null) {
                            Log.w(TAG, "Invalid Response")
                            return
                        }
                        resturants.addAll(body.resturants)
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<YelpSearch>, t: Throwable) {
                        Log.i(TAG, "onFailure $t")
                    }


                })
        }
        else{
            alertDialog("Search term missing", "Search term cannot be empty. Please enter a search term.")
        }

        foodSearchBar.hideKeyboard()
        citySearchBar.hideKeyboard()
    }
    fun alertDialog(title:String,message:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(title)
        dialogBuilder.setMessage(message)
        dialogBuilder.setIcon(android.R.drawable.ic_delete)
        dialogBuilder.setPositiveButton("OKAY"){ dialog, which ->
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
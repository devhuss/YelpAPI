package com.example.yelpapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.resturant_items.view.*

class ResturantsAdapter(val context: Context, val resturants: List<YelpResturant>) :
    RecyclerView.Adapter<ResturantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.resturant_items, parent,false ))
    }

    override fun getItemCount() = resturants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resturant  = resturants[position]
        holder.bind(resturant)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resturant: YelpResturant) {
            itemView.resturantName.text = resturant.name
            itemView.ratingBar3.rating = resturant.rating.toFloat()
            itemView.review_numbers.text = "${resturant.numReviews} Reviews"
            itemView.resturant_address.text = resturant.location.address
            itemView.resturant_category.text = resturant.categories[0].title
            itemView.distance.text = resturant.Distance()
            itemView.price.text = resturant.price
            // adjust to fit picture
            Glide.with(context).load(resturant.imageUrl).apply(
                RequestOptions().transforms(
                CenterCrop(), RoundedCorners(20)
            )).into(itemView.imageView)
        }


    }

}


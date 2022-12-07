package com.example.tattooSelector2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.tattooSelector2.Model.Tattoo
import com.example.tattooSelector2.R
import com.squareup.picasso.Picasso

class MyAdapter(internal var context:Context,internal var data:List<Tattoo>):PagerAdapter() {

    internal var layoutInflater:LayoutInflater
    init {
        layoutInflater =LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.view_pager_items,container,false)
        val tattoo_image = view.findViewById<View>(R.id.tattoo_image) as ImageView
        val tattoo_title = view.findViewById<View>(R.id.tattoo_title) as TextView
        val tattoo_description = view.findViewById<View>(R.id.tattoo_description) as TextView
        Picasso.get().load(data[position].image).into(tattoo_image)
        tattoo_title.text = data[position].name
        tattoo_description.text = data[position].description
        container.addView(view)
        return view
    }
}
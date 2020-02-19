package com.example.myapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_view.view.*

class ImageAdapter constructor(private val mContext: Context, private val images: Array<Int>) :  BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if(convertView == null) {
            val layoutInflater = LayoutInflater.from(mContext)
            convertView = layoutInflater.inflate(R.layout.card_view, null)
        }
        val image = convertView!!.added_picture
        Glide.with(mContext).load(R.drawable.filled_rectangle).into(image).run {
            Log.d("image", "${image.width}")
        }
//        Log.d("image", "${convertView.layoutParams}")
        return convertView
    }

    override fun getItem(position: Int): Any? {
        return images[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return images.size
    }

}
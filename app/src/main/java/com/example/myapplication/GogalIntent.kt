package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*

class GogalIntent internal constructor(private val mContext: Context): Activity() {

    companion object {
        const val CAMERA_REQUEST = 0
        const val GRID_VIEW_CAMERA_REQUEST = 1
        const val PROFILE_IMAGE_GALLERY = 2
        const val GRID_IMAGE_GALLERY = 3
    }


    fun captureImage() {
        val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(callCameraIntent.resolveActivity(mContext.packageManager)!=null){
            (mContext as Activity).startActivityForResult(callCameraIntent, CAMERA_REQUEST)
        }
    }

    fun fromGallery() {
        Log.d("msg", "gallery")
        val galleryIntent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        if(galleryIntent.resolveActivity(mContext.packageManager)!=null) {
            (mContext as Activity).startActivityForResult(
                Intent.createChooser(galleryIntent, "Select image"),
                PROFILE_IMAGE_GALLERY
            )
        }
    }

    fun gridCaptureImage() {
        val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(callCameraIntent.resolveActivity(mContext.packageManager)!=null) {
            (mContext as Activity).startActivityForResult(callCameraIntent, GRID_VIEW_CAMERA_REQUEST)
        }
    }



}
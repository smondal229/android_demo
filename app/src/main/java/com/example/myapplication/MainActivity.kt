package com.example.myapplication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.core.view.setMargins
import androidx.core.view.size
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private var initialGridPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView.adapter = ImageAdapter(this, Array(6, {R.drawable.filled_rectangle} ))

        fun selectImage(position:Int=-1) {
            val options = arrayOf("Take Photo", "Choose from Gallery","Cancel")
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("Choose Image")
                setItems(options) { dialog, index ->
                    when(index) {
                        0 -> {
                                if(position!=-1)
                                {
                                    initialGridPosition = position
                                    GogalIntent(this@MainActivity).gridCaptureImage()
                                }
                                else
                                    GogalIntent(this@MainActivity).captureImage()
                            }
                        1 -> {
                                if(position!=-1)
                                {
                                    dialog.dismiss()
                                }
                                else
                                    GogalIntent(this@MainActivity).fromGallery() }
                        2 -> { dialog.dismiss() }
                    }
                }
                show()
            }
        }



        profile_pic.setOnClickListener {
            selectImage()
        }

        getDOB.setOnClickListener {
            val DOB: Calendar = Calendar.getInstance()
            val day = DOB.get(Calendar.DAY_OF_MONTH)
            val month = DOB.get(Calendar.MONTH)
            val year = DOB.get(Calendar.YEAR)

            val picker = DatePickerDialog(
                this@MainActivity,
                OnDateSetListener { _, year, monthOfYear, dayOfMonth -> getDOB.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day
            )
            picker.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Intent", "$data")
        if (data != null) {
            when (requestCode) {
                GogalIntent.CAMERA_REQUEST -> {
                    val image: Bitmap? = data.extras!!.get("data") as Bitmap
                    Glide.with(this).load(image).apply(RequestOptions.circleCropTransform()).into(profile_pic);
                }

                GogalIntent.PROFILE_IMAGE_GALLERY -> {
                    if (resultCode == RESULT_OK) {
                        Log.d("Gallery", data.data.toString())

//                        val bitmap: Bitmap =
//                            MediaStore.Images.Media.getBitmap(this.contentResolver, data.data)
//                        profile_pic.setImageBitmap(bitmap)

                        val bitmap = when {
                            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                data.data!!
                            )
                            else -> {
                                val source =
                                    ImageDecoder.createSource(this.contentResolver, data.data!!)
                                ImageDecoder.decodeBitmap(source)
                            }
                        }
                        profile_pic.setImageBitmap(bitmap)
                    }
                }

                GogalIntent.GRID_VIEW_CAMERA_REQUEST -> {
                    val image: Bitmap? = data.extras!!.get("data") as Bitmap
                    val iv = gridView[initialGridPosition] as ImageView
                    Log.d("msg", "$iv")
                    iv.setImageBitmap(image)
                }

            }
        }
    }

}

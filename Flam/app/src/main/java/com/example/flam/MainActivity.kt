package com.example.flam

import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.view.TextureView
import androidx.appcompat.app.AppCompatActivity
import com.example.flam.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cameraHelper: CameraHelper

    // Toggle between raw and processed output
    private var showRaw = false

    // FPS counter
    private var lastTime = 0L
    private var frameCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init camera helper
        cameraHelper = CameraHelper(this, binding.textureView.surfaceTextureListener)

        // Toggle button handler
        binding.toggleButton.setOnClickListener {
            showRaw = !showRaw
            binding.toggleButton.text = if (showRaw) "Show Processed" else "Show Raw"
        }

        // Capture frames from TextureView
        binding.textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
                cameraHelper.openCamera(binding.textureView)
            }

            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = false

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                val bmp = binding.textureView.bitmap
                if (bmp != null) {
                    if (showRaw) {
                        // Show raw feed
                        binding.imageView.setImageBitmap(bmp)
                    } else {
                        // Processed output
                        val processed = Bitmap.createBitmap(bmp.width, bmp.height, Bitmap.Config.ARGB_8888)
                        processFrame(bmp, processed)
                        binding.imageView.setImageBitmap(processed)

                        // Save one processed frame (optional)
                        try {
                            val file = File(getExternalFilesDir(null), "processed_frame.png")
                            FileOutputStream(file).use { out ->
                                processed.compress(Bitmap.CompressFormat.PNG, 100, out)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    // FPS counter
                    frameCount++
                    val now = System.currentTimeMillis()
                    if (lastTime == 0L) lastTime = now
                    if (now - lastTime >= 1000) {
                        val fps = frameCount
                        frameCount = 0
                        lastTime = now
                        android.util.Log.d("MainActivity", "FPS: $fps")
                    }
                }
            }
        }
    }

    /**
     * Native JNI method to process a frame using OpenCV in C++
     */
    external fun processFrame(bitmapIn: Bitmap, bitmapOut: Bitmap)

    companion object {
        init {
            System.loadLibrary("flam")
        }
    }
}

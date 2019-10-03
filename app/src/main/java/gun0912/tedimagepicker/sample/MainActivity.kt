package gun0912.tedimagepicker.sample

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.MediaStoreSignature
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.sample.databinding.ActivityMainBinding
import gun0912.tedimagepicker.sample.databinding.ItemImageBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedUriList: List<Uri>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setNormalSingleButton()
        setNormalMultiButton()
    }


    private fun setNormalSingleButton() {
        binding.btnNormalSingle.setOnClickListener {
            TedImagePicker.with(this)
                .screenOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
                .start (
                    { uri -> showSingleImage(uri) },
                    { Log.e(javaClass.simpleName, "single image selection cancelled") }
                )
        }
    }

    private fun setNormalMultiButton() {
        binding.btnNormalMulti.setOnClickListener {
            TedImagePicker.with(this)
                //.mediaType(MediaType.IMAGE)
                //.scrollIndicatorDateFormat("YYYYMMDD")
                //.buttonGravity(ButtonGravity.BOTTOM)
                .screenOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
                .errorListener { message -> Log.d("ted", "message: $message") }
                .selectedUri(selectedUriList)
                .totalAlbumText("All pics")
                .startMultiImage(
                    { list: List<Uri> -> showMultiImage(list) },
                    { Log.e(javaClass.simpleName, "multi image selection cancelled") }
                )
        }
    }

    private fun showSingleImage(uri: Uri) {
        binding.ivImage.visibility = View.VISIBLE
        binding.containerSelectedPhotos.visibility = View.GONE
        Glide.with(this)
            .load(uri)
            .signature(MediaStoreSignature("", File(uri.toString()).lastModified(), 0))
            .into(binding.ivImage)

    }


    private fun showMultiImage(uriList: List<Uri>) {
        this.selectedUriList = uriList
        Log.d("ted", "uriList: $uriList")
        binding.ivImage.visibility = View.GONE
        binding.containerSelectedPhotos.visibility = View.VISIBLE

        binding.containerSelectedPhotos.removeAllViews()

        val viewSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)
                .toInt()
        uriList.forEach {
            val itemImageBinding = ItemImageBinding.inflate(LayoutInflater.from(this))
            Glide.with(this)
                .load(it)
                .signature(MediaStoreSignature("", File(it.toString()).lastModified(), 0))
                .apply(RequestOptions().fitCenter())
                .into(itemImageBinding.ivMedia)
            itemImageBinding.root.layoutParams = FrameLayout.LayoutParams(viewSize, viewSize)
            binding.containerSelectedPhotos.addView(itemImageBinding.root)
        }

    }
}

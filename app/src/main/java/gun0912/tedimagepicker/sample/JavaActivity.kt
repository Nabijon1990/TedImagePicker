package gun0912.tedimagepicker.sample

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener
import gun0912.tedimagepicker.builder.listener.OnSelectedListener

class JavaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TedImagePicker.with(this)
                .screenOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
                .start(object : OnSelectedListener {
                    override fun onSelected(uri: Uri) {

                    }

                    override fun onSelectionCancelled() {
                        Log.w(javaClass.simpleName, "Single selection cancelled")
                    }

                })

        TedImagePicker.with(this)
                .screenOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
                .startMultiImage (object : OnMultiSelectedListener {
                    override fun onSelected(uriList: List<Uri>) {

                    }

                    override fun onSelectionCancelled() {
                        Log.w(javaClass.simpleName, "Multi selection cancelled")
                    }

                })
    }
}

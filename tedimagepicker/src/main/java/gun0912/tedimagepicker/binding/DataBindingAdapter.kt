package gun0912.tedimagepicker.binding

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.MediaStoreSignature
import gun0912.tedimagepicker.base.BaseRecyclerViewAdapter
import java.io.File

internal class DataBindingAdapter {

    companion object {
        @BindingAdapter("imageUri")
        @JvmStatic
        fun loadImage(imageView: ImageView, uri: Uri) {
            Glide.with(imageView.context)
                .load(uri)
                .signature(MediaStoreSignature("", File(uri.path ?: "").lastModified(), 0))
                .thumbnail(0.1f)
                .into(imageView)
        }

        @Suppress("UNCHECKED_CAST")
        @BindingAdapter("replaceAll")
        @JvmStatic
        fun <D> replaceAll(recyclerView: RecyclerView, items: List<D>?) {
            items?.let { (recyclerView.adapter as? BaseRecyclerViewAdapter<D, *>)?.replaceAll(it) }
        }

        @Suppress("UNCHECKED_CAST")
        @BindingAdapter("replaceAll", "diffCallback")
        @JvmStatic
        fun <D> replaceAll(recyclerView: RecyclerView, items: List<D>?, diffCallback: Boolean) {
            items?.let {
                (recyclerView.adapter as? BaseRecyclerViewAdapter<D, *>)?.replaceAll(
                    it,
                    diffCallback
                )
            }
        }


        @Suppress("UNCHECKED_CAST")
        @BindingAdapter("animateTranslationY")
        @JvmStatic
        fun animateTranslationY(view: View, show: Boolean) {
            val animateValue = when (show) {
                true -> -view.y / 2
                false -> 0f
            }
            view.animate().translationY(animateValue).start()
        }

    }

}

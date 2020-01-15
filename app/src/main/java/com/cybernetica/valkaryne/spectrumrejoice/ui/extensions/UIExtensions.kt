package com.cybernetica.valkaryne.spectrumrejoice.ui.extensions

import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.cybernetica.valkaryne.spectrumrejoice.R
import kotlin.math.roundToInt

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutId, this, attachToRoot)
}

fun AppCompatImageView.loadImage(imageUrl: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(context)
        .load(imageUrl)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_close_black_24dp)
        .override(300, 300)
        .apply(RequestOptions().circleCrop())
        .into(this)
}

fun LinearLayout.addLogoToStack(logoRes: Int) {
    val logoImageView = AppCompatImageView(context)
    logoImageView.setImageDrawable(ContextCompat.getDrawable(context, logoRes))
    logoImageView.layoutParams = getStackLayoutParams()
    addView(logoImageView)
}

private fun View.getStackLayoutParams(): LinearLayout.LayoutParams {
    val dm = resources.displayMetrics
    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    lp.marginEnd = convertDipToPx(dm, 5F)
    return lp
}

private fun convertDipToPx(metrics: DisplayMetrics, dip: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics).roundToInt()
}
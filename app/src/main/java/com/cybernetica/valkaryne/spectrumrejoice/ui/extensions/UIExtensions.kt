package com.cybernetica.valkaryne.spectrumrejoice.ui.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(this.context).inflate(layoutId, this, attachToRoot)
}

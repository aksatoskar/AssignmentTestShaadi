package com.example.assignment.util

import android.widget.ImageView
import coil.Coil
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest

@JvmSynthetic
inline fun ImageView.loadWithException(
    imageUri: String?,
    placeHolder: Int
) {
    val request = ImageRequest.Builder(context)
        .data(imageUri)
        .target(
            onSuccess = { result ->
                this.load(result) {
                    placeholder(placeHolder)
                }
            },
            onError = {
                this.load(placeHolder)
            }
        )
        .build()
    Coil.imageLoader(context).enqueue(request)
}

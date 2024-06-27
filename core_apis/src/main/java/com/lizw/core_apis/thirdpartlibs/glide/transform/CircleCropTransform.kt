package com.lizw.core_apis.thirdpartlibs.glide.transform

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest
import kotlin.math.min

/**
 *
 */
class CircleCropTransform : BitmapTransformation() {
    companion object {
        private val ID: String =
            "com.lizw.core_apis.thirdpartlibs.glide.transform.CircleCropTransform"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }

    override fun transform(
        pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int
    ): Bitmap {
        if (toTransform.width == outWidth && toTransform.height == outHeight) {
            return toTransform;
        }

        val size = min(toTransform.width, toTransform.height)

        val x = (toTransform.width - size) / 2
        val y = (toTransform.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(toTransform, x, y, size, size)

        val result = pool[size, size, Bitmap.Config.ARGB_8888]

        val canvas = Canvas(result)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.setShader(BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun equals(other: Any?): Boolean {
        return other is CircleCropTransform
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }
}
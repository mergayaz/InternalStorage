package kz.kuz.internalstorage

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

// цель данного класса - уменьшить размер фотографии под размер экрана
object PictureUtils {
    fun getScaledBitmap(path: String, activity: Activity): Bitmap {
        // получение размера экрана
        val size = Point()
        activity.windowManager.defaultDisplay.getSize(size)
        val screenWidth = size.x
        val screenHeight = size.y
        // закомментировано более современное решение (с API 30), но оно не работает корректно
//        val screenWidth = activity.windowManager.currentWindowMetrics.bounds.width()
//        val screenHeight = activity.windowManager.currentWindowMetrics.bounds.height()

        // чтение размеров изображения на диске
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        // вычисление степени масштабирования
        var inSampleSize = 1
        if (srcHeight > screenHeight || srcWidth > screenWidth) {
            val heightScale = srcHeight / screenHeight
            val widthScale = srcWidth / screenWidth
            inSampleSize = Math.round(if (heightScale > widthScale) heightScale else widthScale)
        }
        options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize

        // чтение данных и создание итогового изображения
        return BitmapFactory.decodeFile(path, options)
    }
}
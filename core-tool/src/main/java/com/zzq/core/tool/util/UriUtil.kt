package com.zzq.core.tool.util

import android.net.Uri
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * 处理[Uri]，同时支持Fragment和Activity
 */
object UriUtil {

    fun Fragment.grabUriMetaData(uri: Uri):String {
        return requireActivity().grabUriMetaData(uri)
    }

    fun FragmentActivity.grabUriMetaData(uri: Uri):String {

        val cursor = contentResolver.query(uri, null, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val displayName = cursor.getString(
                    cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                return displayName
            } else {
                return ""
            }
        } finally {
            cursor?.close()
        }
    }
}
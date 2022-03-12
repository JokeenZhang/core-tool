package com.zzq.core.tool.util

object UrlUtil {
    fun isValidUrl(url: String?): Boolean {
        if (url == null || url.length == 0 || url.trim { it <= ' ' }.length == 0) {
            return false
        }
        var part: String? = ""
        var index = 0
        while (index < url.length) {
            val temp = url.indexOf(".", index + 1)
            if (temp != -1) {
                if (index == 0) {
                    part = url.substring(index, temp)
                    if (isContainsInvalid(part)) {
                        return false
                    }
                } else {
                    part = url.substring(index + 1, temp)
                    if (isContainsInvalid(part)) {
                        return false
                    }
                }
                index = temp
            } else {
                part = url.substring(index + 1)
                if (isContainsInvalid(part)) {
                    return false
                }
                break
            }
        }
        return true
    }

    private fun isContainsInvalid(partUrl: String): Boolean {
        return partUrl.contains("/") || partUrl.contains("，") || partUrl.contains("。")
    }
}
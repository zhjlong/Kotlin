package com.zjl.rengwuxian.kotlindemo.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * created by zhujianlong on 2019-06-25.
 */
class Utils {
    companion object {
        fun dp2px(dp: Float) =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }
}
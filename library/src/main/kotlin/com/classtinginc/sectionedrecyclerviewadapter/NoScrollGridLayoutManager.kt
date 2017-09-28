package com.classtinginc.sectionedrecyclerviewadapter

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet

/**
 * Created by chobyunghoon on 2016. 5. 14..
 */
open class NoScrollGridLayoutManager : GridLayoutManager {

    constructor(context: Context, spanCount: Int) : super(context, spanCount)

    constructor(context: Context, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context, spanCount, orientation, reverseLayout)

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }
}
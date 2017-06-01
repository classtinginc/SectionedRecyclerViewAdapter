package com.classting.basicrecyclerviewadapter

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import butterknife.bindView
import com.classting.classtingcard.consts.enums.FooterType

/**
 * Created by BN on 2016. 1. 21..
 */
open class LoadingFooter : LinearLayout {

    private val loading by bindView<ProgressBar>(R.id.loading)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.footer_loading, this)

        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.layoutParams = params
    }

    fun showFooter(type: FooterType) {
        when (type) {
            FooterType.LOADING -> showLoading()
            FooterType.EMPTY -> showEmpty()
            else -> {}
        }
    }

    private fun showEmpty() {
        loading.visibility = GONE
    }

    private fun showLoading() {
        loading.visibility = VISIBLE
    }
}
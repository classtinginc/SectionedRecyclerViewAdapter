package com.classting.basicrecyclerviewadapter.sectioned_list

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.bindView
import com.classting.basicrecyclerviewadapter.R

/**
 * Created by BN on 2016. 1. 21..
 */
open class SectionItem : LinearLayout {

    private val title by bindView<TextView>(R.id.title)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.view_section, this)

        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.layoutParams = params
    }

    fun bind(title: String) {
        Log.e("test", "bind data : $title")
        this.title.text = title
    }
}
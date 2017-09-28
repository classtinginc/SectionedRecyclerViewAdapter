package com.classtinginc.sectionedrecyclerviewadapter.sectioned_list

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.classtinginc.sectionedrecyclerviewadapter.R
import kotterknife.bindView

/**
 * Created by BN on 2016. 1. 21..
 */
open class RecyclerViewItem : LinearLayout {

    private val title by bindView<TextView>(R.id.title)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflate(context, R.layout.view_item, this)

        val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.layoutParams = params
    }

    open fun bind(data: SampleData?) {
        this.title.text = data?.content?.orEmpty()
    }
}
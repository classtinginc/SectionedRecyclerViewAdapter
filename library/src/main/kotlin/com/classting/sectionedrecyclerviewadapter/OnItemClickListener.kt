package com.classting.sectionedrecyclerviewadapter

import android.view.View

/**
 * Created by BN on 2015. 12. 4..
 */
interface OnItemClickListener {

    fun onClickItemListener(view: View, position: Int)

    fun onLongClickItemListener(view: View, position: Int)
}
package com.classting.sectionedrecyclerviewadapter.basic_list

/**
 * Created by BN on 2015. 12. 14..
 */
interface BasicRecyclerViewView {

    fun notifyDataSetChanged(items: MutableList<String>)
    fun notifyItemRangeInserted(items: MutableList<String>)
    fun showEmptyFooter()
    fun showLoadingFooter()
}
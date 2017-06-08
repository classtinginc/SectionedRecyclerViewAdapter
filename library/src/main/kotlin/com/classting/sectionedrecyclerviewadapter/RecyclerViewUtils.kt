package com.classting.sectionedrecyclerviewadapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by BN on 2015. 12. 15..
 */
class RecyclerViewUtils {

    companion object {
        fun setLinearLayoutManager(context: Context, recyclerView: RecyclerView) {
            recyclerView.layoutManager = LinearLayoutManager(context)
            setScroll(recyclerView)
        }

        fun setHorizontalLayoutManager(context: Context, recyclerView: RecyclerView) {
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setScroll(recyclerView)
        }

        fun setGridLayoutManager(context: Context, recyclerView: RecyclerView, gridCount: Int) {
            setGridLayoutManager(context, recyclerView, gridCount, null)
        }

        fun setGridLayoutManager(context: Context, recyclerView: RecyclerView, gridCount: Int, l: ((position: Int) -> Int)? ) {

            val layoutManager = GridLayoutManager(context, gridCount)
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return l?.let { it(position) } ?: getDefaultSpanSize(position, recyclerView)
                }
            }
            recyclerView.layoutManager = layoutManager
            setScroll(recyclerView)
        }

        fun setNoScrollGridLayoutManager(context: Context, recyclerView: RecyclerView, gridCount: Int, l: ((position: Int) -> Int)? ) {

            val layoutManager = NoScrollGridLayoutManager(context, gridCount)
            layoutManager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return l?.let { it(position) } ?: getDefaultSpanSize(position, recyclerView)
                }
            }
            recyclerView.layoutManager = layoutManager
            setScroll(recyclerView)
        }

        fun setRecyclerViewDivider(context: Context, recyclerView: RecyclerView, orientation: Int) {
            recyclerView.addItemDecoration(DividerItemDecoration(context, orientation))
        }

        fun setRecyclerViewGridDivider(context: Context, recyclerView: RecyclerView, topDivider: Boolean) {
            recyclerView.addItemDecoration(GridDividerDecoration(context, topDivider))
        }

        private fun setScroll(recyclerView: RecyclerView) {
            var scrollPosition = recyclerView.layoutManager?.let { (it as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() } ?: 0
            recyclerView.scrollToPosition(scrollPosition)
        }

        private fun getDefaultSpanSize(position: Int, recyclerView: RecyclerView): Int =
            if (recyclerView.adapter.getItemViewType(position) == RecyclerViewBaseAdapter.TYPE_FOOTER ||
                recyclerView.adapter.getItemViewType(position) == RecyclerViewBaseAdapter.TYPE_HEADER) {
                (recyclerView.layoutManager as GridLayoutManager).spanCount
            } else 1
    }
}
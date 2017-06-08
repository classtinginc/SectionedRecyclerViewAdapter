package com.classting.classtingcard.common.view.listener

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by jay on 1/9/15.
 */
abstract class RecyclerViewScrollListener : RecyclerView.OnScrollListener() {
    private var mScrollThreshold = 40
    private var scrolledDistance = 0

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 2 // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var infiniteScrollingEnabled = true

    private var controlsVisible = true

    // So TWO issues here.
    // 1. When the data is refreshed, we need to change previousTotal to 0.
    // 2. When we switch fragments and it loads itself from some place, for some
    // reason gridLayoutManager returns stale data and hence re-assigning it every time.

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val manager = recyclerView!!.layoutManager

        visibleItemCount = recyclerView.childCount
        if (manager is GridLayoutManager) {
            firstVisibleItem = manager.findFirstVisibleItemPosition()
            totalItemCount = manager.itemCount
        } else if (manager is LinearLayoutManager) {
            firstVisibleItem = manager.findFirstVisibleItemPosition()
            totalItemCount = manager.itemCount
        }

        if (infiniteScrollingEnabled) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }

            if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                // End has been reached
                // do something
                onLoadMore()
                loading = true
            }
        }

//        if (firstVisibleItem == 0) {
//            if (!controlsVisible) {
//                onScrollUp()
//                controlsVisible = true
//            }
//
//            return
//        }
//
//        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
//            onScrollDown()
//            controlsVisible = false
//            scrolledDistance = 0
//        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
//            onScrollUp()
//            controlsVisible = true
//            scrolledDistance = 0
//        }
//
//        if (controlsVisible && dy > 0 || !controlsVisible && dy < 0) {
//            scrolledDistance += dy
//        }
    }

//    fun onScrollUp() {}
//
//    fun onScrollDown() {}

    abstract fun onLoadMore()

    fun setScrollThreshold(scrollThreshold: Int) {
        mScrollThreshold = scrollThreshold
    }

    fun stopInfiniteScrolling() {
        infiniteScrollingEnabled = false
    }

    fun onDataCleared() {
        previousTotal = 0
    }

    companion object {
        private val TAG = "RecylcerScrollListener"
        private val HIDE_THRESHOLD = 20
    }
}
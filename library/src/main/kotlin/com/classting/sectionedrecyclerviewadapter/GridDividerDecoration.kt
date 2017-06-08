package com.classting.sectionedrecyclerviewadapter

/**
 * Created by BN on 2016. 3. 8..
 */

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * ItemDecoration implementation that applies and inset margin
 * around each child of the RecyclerView. It also draws item dividers
 * that are expected from a vertical list implementation, such as
 * ListView.
 */
class GridDividerDecoration : RecyclerView.ItemDecoration {

    private val divider: Drawable
    private var topDivider = false

    constructor(context: Context) {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
    }

    constructor(context: Context, topDivider: Boolean) {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()

        this.topDivider = topDivider
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        drawVertical(c, parent)
        drawHorizontal(c, parent)
    }

    /** Draw dividers at each expected grid interval  */
    fun drawVertical(c: Canvas, parent: RecyclerView) {
        if (parent.adapter.itemCount == 0) return

        val childCount = parent.childCount

        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            if (!isDecorated(child, parent)) {
                return
            }

            if (topDivider && i < (parent.layoutManager as GridLayoutManager).spanCount) {
                drawTopVertical(c, child, params)
            }

            val left = child.left - params.leftMargin
            val right = child.right + params.rightMargin
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    /** Draw dividers to the right of each child view  */
    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount

        for (i in 0..childCount - 1) {
            val child = parent.getChildAt(i)
            if (!isDecorated(child, parent)) {
                return
            }
            val params = child.layoutParams as RecyclerView.LayoutParams

            val left = child.right + params.rightMargin
            val right = left + divider.intrinsicWidth
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    private fun drawTopVertical(c: Canvas, child: View, params: RecyclerView.LayoutParams) {
        val left = child.left - params.leftMargin
        val right = child.right + params.rightMargin
        val top = child.top - params.topMargin
        val bottom = top + divider.intrinsicHeight
        divider.setBounds(left, top, right, bottom)
        divider.draw(c)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        //We can supply forced insets for each item view here in the Rect
//        outRect.set(mInsets, mInsets, mInsets, mInsets)
    }

    private fun isDecorated(child: View?, parent: RecyclerView): Boolean {
        val type = parent.adapter.getItemViewType(parent.getChildAdapterPosition(child))
        return type != RecyclerViewBaseAdapter.TYPE_FOOTER
    }

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }
}
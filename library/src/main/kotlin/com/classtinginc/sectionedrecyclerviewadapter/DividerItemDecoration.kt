package com.classtinginc.sectionedrecyclerviewadapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by BN on 2015. 12. 14..
 */
open class DividerItemDecoration(context: Context, orientation: Int) : RecyclerView.ItemDecoration() {

    companion object {
        private val ATTRS = intArrayOf(android.R.attr.listDivider)

        val HORIZONTAL_LIST = 0
        val VERTICAL_LIST = 1
    }

    private val divider: Drawable
    private var listType: Int = 0

    init {
        val a = context.obtainStyledAttributes(ATTRS)
        divider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        listType = orientation
    }

    override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        when (listType) {
            VERTICAL_LIST -> drawVertical(c, parent)
            HORIZONTAL_LIST -> drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas?, parent: RecyclerView?) {
        val childCount = parent!!.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (!isDecorated(child, parent)) {
                continue
            }
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            val left = parent.paddingLeft + child.paddingLeft
            val right = parent.width - parent.paddingRight - child.paddingRight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    fun drawHorizontal(c: Canvas?, parent: RecyclerView?) {
        val childCount = parent!!.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            if (!isDecorated(child, parent)) {
                continue
            }
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = parent.paddingTop - child.paddingTop
            val bottom = parent.height - parent.paddingBottom + child.paddingBottom
            val left = child.right + params.rightMargin
            val right = left + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView?, state: RecyclerView.State?) {
        when (listType) {
            VERTICAL_LIST -> outRect.set(0, 0, 0, divider.intrinsicHeight)
            HORIZONTAL_LIST -> outRect.set(0, 0, divider.intrinsicWidth, 0)
        }
    }

    open fun isDecorated(child: View?, parent: RecyclerView): Boolean {
        val type = parent.adapter.getItemViewType(parent.getChildAdapterPosition(child))
        return type != RecyclerViewBaseAdapter.TYPE_FOOTER
    }
}

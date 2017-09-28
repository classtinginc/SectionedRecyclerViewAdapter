package com.classtinginc.sectionedrecyclerviewadapter

/**
 * Created by BN on 2015. 11. 18..
 */
open class ViewWrapper<out V : android.view.View?>(val view: V) : android.support.v7.widget.RecyclerView.ViewHolder(view), android.view.View.OnClickListener, android.view.View.OnLongClickListener {

    var onItemClickListener: OnItemClickListener? = null

    init {
        view?.setOnClickListener(this)
        view?.setOnLongClickListener(this)
    }

    override fun onClick(v: android.view.View) {
        if (onItemClickListener != null) {
            onItemClickListener?.onClickItemListener(v, adapterPosition)
        }
    }

    override fun onLongClick(v: android.view.View): Boolean {
        if (onItemClickListener != null) {
            onItemClickListener?.onLongClickItemListener(v, adapterPosition)
        }
        return false
    }
}

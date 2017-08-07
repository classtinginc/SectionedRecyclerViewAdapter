package com.classtinginc.sectionedrecyclerviewadapter.sectioned_list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.classtinginc.sectionedrecyclerviewadapter.LoadingFooter
import com.classtinginc.classtingcard.consts.enums.FooterType
import com.classtinginc.sectionedrecyclerviewadapter.RecyclerViewBaseAdapter
import com.classtinginc.sectionedrecyclerviewadapter.ViewWrapper

/**
 * Created by BN on 2015. 12. 1..
 */
class SectionedAdapter(context: Context) : RecyclerViewBaseAdapter<SampleData>(context) {

    private var footerType: FooterType = FooterType.EMPTY

    override fun onCreateItemView(parent: ViewGroup, viewType: Int): View = RecyclerViewItem(context)

    override fun onCreateHeaderView(parent: ViewGroup, viewType: Int): View = null!!

    override fun onCreateFooterView(parent: ViewGroup, viewType: Int): View = LoadingFooter(context)

    override fun useFooter(): Boolean = true

    override fun useHeader(): Boolean = false

    override fun onBindViewHolder(holder: ViewWrapper<View>, position: Int) {
        holder.onItemClickListener = listener
        when (getItemViewType(position)) {
            TYPE_FOOTER -> bindFooter(holder)
            TYPE_DEFAULT -> bindItem(holder, position)
        }
    }

    private fun bindItem(holder: ViewWrapper<View>, position: Int) {
        val item = holder.view as RecyclerViewItem
        item.bind(getItem(position))
    }

    private fun bindFooter(holder: ViewWrapper<View>) {
        val footer = holder.view as LoadingFooter
        footer.showFooter(footerType)
    }
}

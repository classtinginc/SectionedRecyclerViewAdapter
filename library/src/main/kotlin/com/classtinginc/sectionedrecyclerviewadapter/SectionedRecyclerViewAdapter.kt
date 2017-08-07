package com.classtinginc.sectionedrecyclerviewadapter

import android.view.View

class SectionedRecyclerViewAdapter<T>(context: android.content.Context,
                                      val baseAdapter: RecyclerViewBaseAdapter<T>,
                                      val sectionizer: Sectionizer<T>) : RecyclerViewBaseAdapter<Section>(context) {

    companion object {
        val TYPE_SECTION = 3
    }

    override fun onCreateHeaderView(parent: android.view.ViewGroup, viewType: Int): android.view.View = baseAdapter.onCreateHeaderView(parent, viewType)

    override fun onCreateFooterView(parent: android.view.ViewGroup, viewType: Int): android.view.View = baseAdapter.onCreateFooterView(parent, viewType)

    override fun useFooter(): Boolean = baseAdapter.useFooter()

    override fun useHeader(): Boolean = baseAdapter.useHeader()

    private var valid = true

    private val sections = android.util.SparseArray<Section>()

    init {
        baseAdapter.registerAdapterDataObserver(object : android.support.v7.widget.RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                valid = baseAdapter.itemCount > 0
                notifyAllItemsChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                valid = baseAdapter.itemCount > 0
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                valid = baseAdapter.itemCount > 0
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                valid = baseAdapter.itemCount > 0
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })

        notifyAllDataSetChanged()
    }

    override fun onCreateItemView(parent: android.view.ViewGroup, viewType: Int): android.view.View {
        if (viewType == SectionedRecyclerViewAdapter.Companion.TYPE_SECTION) {
            return sectionizer.onCreateItemView(parent)
        }
        return baseAdapter.onCreateItemView(parent, viewType)
    }

    override fun onBindViewHolder(holder: ViewWrapper<View>, position: Int) {
        if (getItemViewType(position) == SectionedRecyclerViewAdapter.Companion.TYPE_SECTION) {
            sectionizer.onBindViewHolder(holder, position, sections.get(position).title.orEmpty())
        } else {
            baseAdapter.onBindViewHolder(holder, getItemPosition(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (isSectionHeaderPosition(position)) {
            return SectionedRecyclerViewAdapter.Companion.TYPE_SECTION
        }
        return baseAdapter.getItemViewType(getItemPosition(position))
    }

    fun getSectionedPosition(position: Int): Int {
        return (position + (if (useHeader()) 1 else 0)).let { pos ->
            pos + (0..sections.size() - 1)
                .takeWhile { sections.valueAt(it).firstPosition <= pos }
                .count()
        }
    }

    private fun getItemPosition(sectionedPosition: Int): Int {
        if (isSectionHeaderPosition(sectionedPosition)) {
            return android.support.v7.widget.RecyclerView.NO_POSITION
        }
        return getPurePosition(sectionedPosition)
    }

    fun getPurePosition(sectionedPosition: Int): Int {
        return sectionedPosition -
            (0..sections.size() - 1)
                .takeWhile { sections.valueAt(it).sectionedPosition <= sectionedPosition }
                .count()
    }

    fun isSectionHeaderPosition(position: Int): Boolean {
        return sections.get(position) != null
    }

    fun getBaseItem(sectionedPosition: Int): T? {
        val position = getPurePosition(sectionedPosition)
        if (getItemViewType(position) == RecyclerViewBaseAdapter.Companion.TYPE_HEADER) {
            return null
        }
        if (getItemViewType(position) == RecyclerViewBaseAdapter.Companion.TYPE_FOOTER) {
            return null
        }
        return baseAdapter.listItems[position - (if (useHeader()) 1 else 0)]
    }

    override fun getItemId(position: Int): Long {
        return if (isSectionHeaderPosition(position))
            (Integer.MAX_VALUE - sections.indexOfKey(position)).toLong()
        else
            baseAdapter.getItemId(getItemPosition(position))
    }

    override fun getItemCount(): Int {
        return (if (valid) baseAdapter.itemCount + sections.size() else 0)
    }

    private fun generateSection(sections: MutableList<Section>) {
        this.sections.clear()

        sections.sortWith(java.util.Comparator { o, o1 ->
            if (o.firstPosition == o1.firstPosition) {
                0
            } else if (o.firstPosition < o1.firstPosition) {
                -1
            } else {
                1
            }
        })

        var offset = 0

        sections.forEach {
            it.sectionedPosition = it.firstPosition + offset
            this.sections.append(it.sectionedPosition, it)
            ++offset
        }
    }

    private fun getSections(): MutableList<Section> {
        val sections = mutableListOf<Section>()

        (0..baseAdapter.itemCount - 1)
            .filter { baseAdapter.getItemViewType(it) >= RecyclerViewBaseAdapter.Companion.TYPE_DEFAULT }
            .forEach { i ->
                baseAdapter.getItem(i)?.let {
                    val sectionName = sectionizer.getSectionTitleForItem(it)
                    val section = Section(i, sectionName)

                    if (!sections.map { it.title }.contains(section.title)) {
                        sections.add(section)
                    }
                }
            }
        return sections
    }

    fun notifyAllDataSetChanged() {
        generateSection(getSections())
        notifyDataSetChanged()
    }

    override fun notifyItemsRangeInserted(position: Int, size: Int) {
        generateSection(getSections())
        notifyItemRangeInserted(getSectionedPosition(position), size)
    }

    fun notifyItemsRangeInsertedWithSection(position: Int, size: Int) {
        generateSection(getSections())
        notifyItemRangeInserted(getSectionedPosition(position) - 1, size + 1)
    }

    override fun notifyItemDataChanged(position: Int) {
        notifyItemChanged(getSectionedPosition(position))
    }

    fun notifyItemDataChangedWithSection(position: Int, size: Int) {
        notifyItemChanged(getSectionedPosition(position) - 1, size + 1)
    }

    fun notifyItemsRangeChanged(position: Int, size: Int) {
        notifyItemRangeChanged(getSectionedPosition(position), size)
    }

    override fun notifyAllItemsChanged() {
        super.notifyAllItemsChanged()
    }

    override fun notifyItemDataRemoved(position: Int) {
        notifyItemsRangeRemoved(position, 1)
    }

    fun notifyItemsRangeRemoved(position: Int, size: Int) {
        generateSection(getSections())
        notifyItemRangeRemoved(getSectionedPosition(position), size)
    }

    fun notifyItemsRangeRemovedWithSection(position: Int, size: Int) {
        generateSection(getSections())
        notifyItemRangeRemoved(getSectionedPosition(position) - 1, size + 1)
    }
}

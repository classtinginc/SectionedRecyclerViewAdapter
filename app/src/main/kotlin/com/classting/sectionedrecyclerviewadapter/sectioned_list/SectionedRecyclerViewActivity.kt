package com.classting.sectionedrecyclerviewadapter.sectioned_list

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import com.classting.sectionedrecyclerviewadapter.R
import com.classting.library.*
import com.classting.library.SectionedRecyclerViewAdapter

/**
 * Created by BN on 2015. 12. 1..
 */
class SectionedRecyclerViewActivity : AppCompatActivity(), SectionedRecyclerViewView, OnItemClickListener {

    private val recyclerView by bindView<RecyclerView>(R.id.recycler_view)

    private val adapter by lazy {
        SectionedAdapter(this).apply {
            listener = this@SectionedRecyclerViewActivity
        }
    }
    private val presenter by lazy {
        SectionedRecyclerViewPresenter().apply {
            view = this@SectionedRecyclerViewActivity
        }
    }
    private val sectionedAdapter by lazy {
        SectionedRecyclerViewAdapter(
            this,
            adapter,
            SampleSectionizer(this@SectionedRecyclerViewActivity)).apply {

        }
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        loadView()
        presenter.getData()
    }

    private fun loadView() {
        RecyclerViewUtils.setLinearLayoutManager(this, recyclerView)
        RecyclerViewUtils.setRecyclerViewDivider(this, recyclerView, DividerItemDecoration.VERTICAL_LIST)
        recyclerView.adapter = sectionedAdapter
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun notifyDataSetChanged(items: MutableList<SampleData>) {
        adapter.listItems = items
        sectionedAdapter.notifyAllDataSetChanged()
    }

    override fun onClickItemListener(view: View, position: Int) {}

    override fun onLongClickItemListener(view: View, position: Int) {}

    private inner class SampleSectionizer(internal var context: Context) : Sectionizer<SampleData> {

        override fun onCreateItemView(parent: ViewGroup): View = SectionItem(context)

        override fun onBindViewHolder(holder: ViewWrapper<View>, position: Int, sectionName: String) {
            if (holder.view is SectionItem) {
                (holder.view as SectionItem).bind(sectionName)
            }
        }

        override fun getSectionTitleForItem(model: SampleData): String = model.section
    }
}

package com.classting.basicrecyclerviewadapter.basic_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.bindView
import com.classting.basicrecyclerviewadapter.R
import com.classting.classtingcard.common.view.listener.RecyclerViewScrollListener
import com.classting.library.DividerItemDecoration
import com.classting.library.OnItemClickListener
import com.classting.library.RecyclerViewUtils

/**
 * Created by BN on 2015. 12. 1..
 */
class BasicRecyclerViewActivity : AppCompatActivity(), BasicRecyclerViewView, OnItemClickListener {

    private val recyclerView by bindView<RecyclerView>(R.id.recycler_view)

    private val adapter by lazy {
        BasicRecyclerViewAdapter(this).apply {
            listener = this@BasicRecyclerViewActivity
        }
    }
    private val presenter by lazy {
        BasicRecyclerViewPresenter().apply {
            view = this@BasicRecyclerViewActivity
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
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun notifyDataSetChanged(items: MutableList<String>) {
        adapter.notifyDataSetChanged(items)
    }

    override fun notifyItemRangeInserted(items: MutableList<String>) {
        adapter.notifyItemRangeInserted(items)
    }

    override fun showEmptyFooter() {
        adapter.showEmptyFooter()
    }

    override fun showLoadingFooter() {
        adapter.showLoadingFooter()
    }

    override fun onClickItemListener(view: View, position: Int) {}

    override fun onLongClickItemListener(view: View, position: Int) {}

    private val scrollListener = object : RecyclerViewScrollListener() {
        override fun onLoadMore() {
            recyclerView.post {
                presenter.getData(true)
            }
        }
    }
}

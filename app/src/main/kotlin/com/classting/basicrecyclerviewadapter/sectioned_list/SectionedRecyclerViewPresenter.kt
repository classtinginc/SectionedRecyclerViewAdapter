package com.classting.basicrecyclerviewadapter.sectioned_list

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * Created by BN on 2015. 12. 8..
 */
class SectionedRecyclerViewPresenter {
    private val SECTION_COUNT = 5
    private val ITEM_COUNT = 3

    private val subscription by lazy { CompositeSubscription() }
    var view: SectionedRecyclerViewView? = null

    fun onDetach() {
        subscription.unsubscribe()
    }

    fun getData() {
        subscription.add(Observable.create<MutableList<SampleData>> { subscriber ->
            val data = mutableListOf<SampleData>()
            (1..SECTION_COUNT).forEachIndexed { sectionedIndex, i ->
                (0..ITEM_COUNT - 1).forEachIndexed { index, i ->
                    data.add(SampleData("section$sectionedIndex", "item$index"))
                }
            }
            subscriber.onNext(data)
        }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ data ->
                    view?.notifyDataSetChanged(data)
                }, { e ->
                    e.printStackTrace()
                }))
    }
}
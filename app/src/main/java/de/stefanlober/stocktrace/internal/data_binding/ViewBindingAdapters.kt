package de.stefanlober.stocktrace.internal.data_binding

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import de.stefanlober.stocktrace.base.BaseAdapter
import de.stefanlober.stocktrace.base.ListAdapterItem

@BindingAdapter("setAdapter")
fun setAdapter(recyclerView: RecyclerView, adapter: BaseAdapter<ViewDataBinding, ListAdapterItem>?) {
    adapter?.let {
        recyclerView.adapter = it
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("submitList")
fun submitList(recyclerView: RecyclerView, list: List<ListAdapterItem>?) {
    val adapter = recyclerView.adapter as BaseAdapter<ViewDataBinding, ListAdapterItem>?
    adapter?.updateData(list ?: listOf())
}

@BindingAdapter("manageState")
fun manageState(progressBar: ProgressBar, state: Boolean) {
    progressBar.visibility = if (state) View.VISIBLE else View.GONE
}
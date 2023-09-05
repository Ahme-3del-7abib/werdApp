package com.ahmed.a.habib.werdapp.features.fixedWerd

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.databinding.ItemListWerdBinding
import com.ahmed.a.habib.werdapp.utils.BaseRVAdapter
import com.ahmed.a.habib.werdapp.utils.PublicKeys.Companion.fontPath
import com.ahmed.a.habib.werdapp.utils.setTypeFace
import com.ahmed.a.habib.werdapp.utils.stringToBitMap

class WerdRecyclerAdapter(private val context: Context) :
    BaseRVAdapter<WerdDataItems, ItemListWerdBinding>(CallBack()) {

    override fun getLayout(): Int = R.layout.item_list_werd

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemListWerdBinding>,
        position: Int
    ) {
        val item = getItem(position)
        holder.binding.titleTV.text = item.werdName

        if (!item.werdIcon.isNullOrEmpty()) {
            holder.binding.iconID.visibility = View.VISIBLE
            holder.binding.iconID.setImageBitmap(item.werdIcon?.stringToBitMap())
        }

        setOnClickListener(holder, position)

        context.setTypeFace(
            listOf(
                holder.binding.titleTV,
            ), font = fontPath
        )
    }

    private fun setOnClickListener(
        holder: BaseViewHolder<ItemListWerdBinding>,
        position: Int
    ) {
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, getItem(position), position)
        }
    }

    class CallBack : DiffUtil.ItemCallback<WerdDataItems>() {
        override fun areItemsTheSame(oldItem: WerdDataItems, newItem: WerdDataItems): Boolean {
            return oldItem._ID == newItem._ID
        }

        override fun areContentsTheSame(oldItem: WerdDataItems, newItem: WerdDataItems): Boolean {
            return oldItem == newItem
        }
    }
}
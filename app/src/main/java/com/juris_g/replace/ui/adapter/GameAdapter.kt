package com.juris_g.replace.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juris_g.replace.databinding.GamePieceBinding
import com.juris_g.replace.ui.models.GamePieceUIModel
import kotlin.properties.Delegates

class GameAdapter(private val onItemClick: (noteItem: GamePieceUIModel) -> Unit) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    var numbers: List<GamePieceUIModel> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(DifferenceUtil(old, new)).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        GamePieceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GameAdapter.ViewHolder, position: Int) {
        val item = numbers[position]
        holder.binding.number.tag = item
        holder.binding.item = item

        holder.binding.number.setOnClickListener { cardView ->
            onItemClick(cardView.tag as GamePieceUIModel)
        }
    }

    override fun getItemCount() = numbers.size

    inner class ViewHolder(val binding: GamePieceBinding) : RecyclerView.ViewHolder(binding.root)

    inner class DifferenceUtil(private val old: List<GamePieceUIModel>, private val aNew: List<GamePieceUIModel>) : DiffUtil.Callback() {
        override fun getOldListSize() = old.size

        override fun getNewListSize() = aNew.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old[oldItemPosition].id == aNew[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            old[oldItemPosition] == aNew[newItemPosition]
    }
}
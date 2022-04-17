package com.juris_g.replace.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.juris_g.replace.common.AdapterDiff
import com.juris_g.replace.databinding.GamePieceBinding
import com.juris_g.replace.databinding.GameTurnsBinding
import com.juris_g.replace.ui.models.GamePieceUIModel
import com.juris_g.replace.ui.models.GameTurnModel
import kotlin.properties.Delegates

class GameTurnsAdapter : RecyclerView.Adapter<GameTurnsAdapter.ViewHolder>() {

    var moves: List<GameTurnModel> by Delegates.observable(emptyList()) { _, old, new ->
        DiffUtil.calculateDiff(
            AdapterDiff(old, new) { oldItem, newItem ->
                oldItem.id == newItem.id
            }
        ).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        GameTurnsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: GameTurnsAdapter.ViewHolder, position: Int) {
        val item = moves[position]
        holder.binding.move.tag = item
        holder.binding.item = item
    }

    override fun getItemCount() = moves.size

    inner class ViewHolder(val binding: GameTurnsBinding) : RecyclerView.ViewHolder(binding.root)
}
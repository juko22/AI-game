package com.juris_g.replace.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juris_g.replace.common.launchIO
import com.juris_g.replace.common.launchMain
import com.juris_g.replace.common.launchUI
import com.juris_g.replace.databinding.GameFragmentBinding
import com.juris_g.replace.ui.adapter.GameAdapter
import timber.log.Timber

class GameFragment : BaseFragment() {

    private lateinit var binding: GameFragmentBinding

    private val adapter by lazy {
        GameAdapter { piece ->
            viewModel.gamePieceClicked(piece)
            Timber.d("Piece clicked")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GameFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberList.adapter = adapter

        viewModel.startGame()
        launchMain {
            viewModel.gamePieces.collect { gamePieces ->
                adapter.numbers = gamePieces
                Timber.d("Game pieces collected: $gamePieces")
            }
        }
    }
}
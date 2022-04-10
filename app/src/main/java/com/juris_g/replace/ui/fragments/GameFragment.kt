package com.juris_g.replace.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juris_g.replace.App
import com.juris_g.replace.R
import com.juris_g.replace.common.launchMain
import com.juris_g.replace.common.launchUI
import com.juris_g.replace.common.openFragment
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
    ): View {
        binding = GameFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberList.adapter = adapter
        adapter.numbers = emptyList()
        binding.numberList.itemAnimator = null

        binding.combineNumbers.setOnClickListener {
            viewModel.makeTheMove()
        }

        launchUI {
            viewModel.gamePieces.collect { gamePieces ->
                adapter.numbers = gamePieces
                Timber.d("Game pieces collected: $gamePieces")
                binding.points.text = "Points: " + viewModel.getPoints()
                if (gamePieces.size == 1) {
                    viewModel.gameOver(gamePieces.first().number)
                }
            }
        }

        launchUI {
            viewModel.playerHasWon.collect { playerWon ->
                Timber.d("Did player won: $playerWon")
                AlertDialog.Builder(context).setMessage(
                    if (playerWon) getString(R.string.player_won)
                    else getString(R.string.phone_won)).setPositiveButton("Ok") { popup, _ ->
                    popup.dismiss()
                    viewModel.clearRepo()
                    App.clearViewModel()
                    openFragment(R.id.main_fragment)
                }.setCancelable(false).show()
            }
        }
    }
}
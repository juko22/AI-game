package com.juris_g.replace.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juris_g.replace.databinding.GameFragmentBinding
import com.juris_g.replace.ui.adapter.GameAdapter
import timber.log.Timber

class GameFragment : BaseFragment() {

    private lateinit var binding: GameFragmentBinding

    private val adapter by lazy {
        GameAdapter { number ->
            number.isSelected = !number.isSelected
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

        adapter.numbers = viewModel.startGame()
    }
}
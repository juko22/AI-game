package com.juris_g.replace.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juris_g.replace.R
import com.juris_g.replace.common.openFragment
import com.juris_g.replace.databinding.StartFragmentBinding
import com.juris_g.replace.ui.fragments.BaseFragment

class StartFragment : BaseFragment() {

    private lateinit var binding: StartFragmentBinding

    override fun onCreateView
                (inflater: LayoutInflater,
                 container: ViewGroup?,
                 savedInstanceState: Bundle?
    ): View {
        binding = StartFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startGamePhone.setOnClickListener {
            viewModel.playerFirst = false
            openFragment(R.id.game_fragment)
        }
        binding.startGamePlayer.setOnClickListener {
            viewModel.playerFirst = true
            openFragment(R.id.game_fragment)
        }
    }
}
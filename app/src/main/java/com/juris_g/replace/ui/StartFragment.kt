package com.juris_g.replace.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juris_g.replace.R
import com.juris_g.replace.common.openFragment
import com.juris_g.replace.databinding.StartFragmentBinding

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

        binding.startGame.setOnClickListener {
            openFragment(R.id.game_fragment)
        }
    }
}
package com.juris_g.replace.ui.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import com.juris_g.replace.App
import com.juris_g.replace.common.lazyViewModel
import com.juris_g.replace.injection.viewModelProvider
import com.juris_g.replace.repository.GameRepository
import javax.inject.Inject

open class BaseFragment : Fragment() {

    @Inject lateinit var repository: GameRepository

    val viewModel by lazyViewModel(
        { requireActivity().application as App },
        { viewModelProvider.provideGameViewModel(repository) }
    )

    override fun onAttach(context: Context) {
        App.component.inject(this)
        super.onAttach(context)
    }

}
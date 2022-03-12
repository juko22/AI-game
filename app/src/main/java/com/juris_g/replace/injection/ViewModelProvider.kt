package com.juris_g.replace.injection

import androidx.annotation.RestrictTo
import com.juris_g.replace.repository.GameRepository
import com.juris_g.replace.ui.GameViewModel

var viewModelProvider = ViewModelProvider()
    private set

@RestrictTo(RestrictTo.Scope.TESTS)
fun setupViewModelProvider(testInjector: ViewModelProvider) {
    viewModelProvider = testInjector
}

open class ViewModelProvider {

    open fun provideGameViewModel(repository: GameRepository) = GameViewModel(repository)
}
package com.juris_g.replace.injection

import com.juris_g.replace.ui.fragments.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [InjectionModule::class])
interface InjectionComponent {
    fun inject(target: BaseFragment)
}
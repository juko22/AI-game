package com.juris_g.replace

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.juris_g.replace.common.LineNumberDebugTree
import com.juris_g.replace.injection.DaggerInjectionComponent
import com.juris_g.replace.injection.InjectionComponent
import com.juris_g.replace.injection.InjectionModule
import timber.log.Timber

open class App : Application(), ViewModelStoreOwner {

    override fun onCreate() {
        super.onCreate()
        component = makeComponent()
        if (BuildConfig.DEBUG) {
            Timber.plant(LineNumberDebugTree())
        }
    }

    override fun getViewModelStore() = appViewModelStore

    open fun makeComponent(): InjectionComponent =
        DaggerInjectionComponent.builder().injectionModule(InjectionModule(this)).build()

    companion object {

        private val appViewModelStore: ViewModelStore by lazy { ViewModelStore() }


        lateinit var component: InjectionComponent
            private set

        fun clearViewModel() = appViewModelStore.clear()
    }

}
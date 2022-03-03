package com.juris_g.replace

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.juris_g.replace.common.LineNumberDebugTree
import com.juris_g.replace.injection.InjectionComponent
import timber.log.Timber

class App : Application(), ViewModelStoreOwner {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(LineNumberDebugTree())
        }
    }

    override fun getViewModelStore() = appViewModelStore

    companion object {

        private val appViewModelStore: ViewModelStore by lazy { ViewModelStore() }

        lateinit var compiler: InjectionComponent
            private set

        fun clearViewModel() = appViewModelStore.clear()
    }

}
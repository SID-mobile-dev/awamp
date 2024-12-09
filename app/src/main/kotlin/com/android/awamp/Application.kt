package com.android.awamp

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.android.awamp.di.appModule
import com.android.awamp.domain.MainInteractor
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {

    private val applicationObserver by inject<ApplicationObserver>()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule)
        }

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(applicationObserver)
    }
}

class ApplicationObserver(private val mainInteractor: MainInteractor) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        owner.lifecycleScope.launch {
            mainInteractor.setFields()
        }
    }
}

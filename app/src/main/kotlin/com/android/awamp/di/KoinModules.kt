package com.android.awamp.di

import androidx.room.Room
import com.android.awamp.ApplicationObserver
import com.android.awamp.database.AppDatabase
import com.android.awamp.database.FieldDao
import com.android.awamp.domain.MainInteractor
import com.android.awamp.storage.SharedPreferenceStorage
import com.android.awamp.storage.SharedPreferenceStorageImpl
import com.android.awamp.viewmodel.chooseSide.ChooseSideViewModel
import com.android.awamp.viewmodel.initial.InitialViewModel
import com.android.awamp.viewmodel.input.InputViewModel
import com.android.awamp.viewmodel.main.MainViewModel
import com.android.awamp.viewmodel.result.ResultViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ApplicationObserver> { ApplicationObserver(get()) }
    single<SharedPreferenceStorage> { SharedPreferenceStorageImpl(get()) }

    factory<MainInteractor> { MainInteractor(get()) }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "database",
        ).build()
    }
    single<FieldDao> {
        val database = get<AppDatabase>()
        database.getFieldDao()
    }

    viewModel { InitialViewModel(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { ChooseSideViewModel() }
    viewModel { params -> InputViewModel(get(), get(), get(), params.get()) }
    viewModel { ResultViewModel(get(), get(), get()) }
}

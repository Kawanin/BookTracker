package com.example.BookTracker.di

import com.example.BookTracker.data.getRoomDatabase
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.core.module.dsl.*
import com.example.BookTracker.presentation.screen.home.HomeViewModel
import com.example.BookTracker.presentation.screen.manage.ManageViewModel
import com.example.BookTracker.presentation.screen.details.DetailsViewModel

expect val targetModule: Module

val sharedModule = module {
    single { getRoomDatabase(get()) }
    viewModelOf(::HomeViewModel)
    viewModelOf(::ManageViewModel)
    viewModelOf(::DetailsViewModel)
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}
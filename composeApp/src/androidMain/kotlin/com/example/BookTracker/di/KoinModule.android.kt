package com.example.BookTracker.di

import com.example.BookTracker.database.getDatabaseBuilder
import org.koin.dsl.module

actual val targetModule = module {
    single { getDatabaseBuilder(context = get()) }
}
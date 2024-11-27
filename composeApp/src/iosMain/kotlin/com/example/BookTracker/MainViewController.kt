package com.example.BookTracker

import androidx.compose.ui.window.ComposeUIViewController
import com.example.BookTracker.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }
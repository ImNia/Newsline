package com.delirium.newsline.di

import com.delirium.newsline.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<NewsViewModel> {
        NewsViewModel()
    }
}
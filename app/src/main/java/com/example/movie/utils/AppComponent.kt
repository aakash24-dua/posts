package com.example.movie.utils

import com.example.movie.App
import com.example.movie.ui.main.view.MainActivity
import com.example.movie.ui.main.viewmodel.MainActivityViewModel
import com.example.movie.ui.movieDetail.view.PostDetailActivity
import com.example.movie.ui.movieDetail.viewmodel.PostDetailViewmodel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(application: App)

    fun inject(activity: MainActivity)
    fun inject(activity: PostDetailActivity)

    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: PostDetailViewmodel)

    fun networkService(): NetworkService

}
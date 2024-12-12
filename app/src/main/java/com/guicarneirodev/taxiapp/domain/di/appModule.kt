package com.guicarneirodev.taxiapp.domain.di

import com.guicarneirodev.taxiapp.data.repository.RideRepositoryImpl
import com.guicarneirodev.taxiapp.data.api.RideApi
import com.guicarneirodev.taxiapp.data.di.NetworkModule
import com.guicarneirodev.taxiapp.data.repository.RideRepository
import com.guicarneirodev.taxiapp.domain.error.ErrorHandler
import com.guicarneirodev.taxiapp.domain.validators.ConfirmRideValidator
import com.guicarneirodev.taxiapp.domain.validators.DriverValidator
import com.guicarneirodev.taxiapp.domain.validators.EstimateValidator
import com.guicarneirodev.taxiapp.domain.usecase.ConfirmRideUseCase
import com.guicarneirodev.taxiapp.domain.usecase.EstimateRideUseCase
import com.guicarneirodev.taxiapp.domain.usecase.GetRideHistoryUseCase
import com.guicarneirodev.taxiapp.presentation.screens.history.viewmodel.RideHistoryViewModel
import com.guicarneirodev.taxiapp.presentation.screens.options.viewmodel.RideOptionsViewModel
import com.guicarneirodev.taxiapp.presentation.screens.request.viewmodel.RideRequestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single { NetworkModule.provideOkHttpClient() }
    single { NetworkModule.provideRetrofit(get()) }
    single { get<Retrofit>().create(RideApi::class.java) }

    single<RideRepository> { RideRepositoryImpl(get()) }

    single { EstimateValidator() }
    single { ConfirmRideValidator() }
    single { DriverValidator() }

    single { ErrorHandler() }

    factory {
        EstimateRideUseCase(
            repository = get(),
            validator = get<EstimateValidator>()
        )
    }
    factory {
        ConfirmRideUseCase(
            repository = get(),
            confirmValidator = get<ConfirmRideValidator>(),
            driverValidator = get<DriverValidator>()
        )
    }
    factory {
        GetRideHistoryUseCase(
            repository = get(),
            errorHandler = get()
        )
    }

    viewModel { RideRequestViewModel(get()) }
    viewModel { RideOptionsViewModel(get()) }
    viewModel { RideHistoryViewModel(get()) }
}
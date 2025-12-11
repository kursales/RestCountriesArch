package com.kursales.country_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursales.domain.interactors.RestCountriesInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = CountryDetailsViewModel.Factory::class)
class CountryDetailsViewModel @AssistedInject constructor(
    @Assisted id: Int,
    interactor: RestCountriesInteractor,
) : ViewModel() {

    val countryDetailsState = interactor.observeCountryDetails(id)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(100L), null)

    @AssistedFactory
    interface Factory {
        fun create(id: Int): CountryDetailsViewModel
    }
}
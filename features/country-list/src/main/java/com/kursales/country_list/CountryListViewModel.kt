package com.kursales.country_list

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kursales.core.error.ConnectionException
import com.kursales.core.error.LogicError
import com.kursales.core.result.fold
import com.kursales.core.result.onFailure
import com.kursales.domain.entities.Country
import com.kursales.domain.interactors.RestCountriesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val interactor: RestCountriesInteractor,
) : ViewModel() {

    private val countries = interactor.observeCountries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())

    private val _searchState = MutableStateFlow("")
    val searchState = _searchState.asStateFlow()

    private val _isSearchEnabled = MutableStateFlow(false)
    val isSearchEnabled = _isSearchEnabled.asStateFlow()

    val countriesState =
        combine(countries, _isSearchEnabled, _searchState) { countries, isSearch, query ->
            if (isSearch) {
                countries.filter {
                    it.name.official.lowercase().contains(query.lowercase())
                }
            } else {
                countries
            }
        }


    private val _errorState = MutableStateFlow<DialogState>(DialogState.None)
    val errorState = _errorState.asStateFlow()

    private val _refreshState = MutableStateFlow(false)
    val refreshState = _refreshState.asStateFlow()


    init {
        refresh()
    }

    fun refresh() {
        if (!checkConnection()) {
            _errorState.value = DialogState.Connection
            return
        }
        viewModelScope.launch {
            _refreshState.value = true
            interactor.refreshCountries().onFailure {
                _refreshState.value = false
                _errorState.value = when (it) {
                    is LogicError -> DialogState.Logic(it.text.get(context).toString())
                    else -> DialogState.Logic(
                        it.message ?: context.getString(R.string.unknown_error)
                    )
                }
            }
            _refreshState.value = false
        }
    }

    fun checkConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

    fun changeSearchState() {
        _isSearchEnabled.update { !it }
    }

    fun changeSearch(query: String) {
        _searchState.value = query
    }

    fun hideErrorDialog() {
        _errorState.value = DialogState.None
    }
}
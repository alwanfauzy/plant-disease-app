package com.alwan.plantdisease.ui.main

import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alwan.plantdisease.core.data.Resource
import com.alwan.plantdisease.core.domain.entity.WeatherInfo
import com.alwan.plantdisease.core.domain.usecase.WeatherUseCase
import com.alwan.plantdisease.util.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherUseCase: WeatherUseCase) :
    ViewModel() {

    private val _location = MutableLiveData<Location?>(null)
    val location: LiveData<Location?> = _location
    private val _city = MutableLiveData<String?>(null)
    val city: LiveData<String?> = _city
    val weather = Transformations.switchMap(location) { location ->
        location?.let { weatherUseCase.getWeather(it.latitude, it.longitude).asLiveData() }
    }

    fun setLocation(location: Location) = _location.postValue(location)

    fun setCity(city: String) = _city.postValue(city)

}
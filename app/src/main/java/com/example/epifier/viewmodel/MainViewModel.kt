package com.example.epifier.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.epifier.extention.Resource
import com.example.epifier.repository.local.DetailRepository
import com.example.epifier.repository.model.Detail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    private val _pan = MutableStateFlow("")
    private val _day = MutableStateFlow("")
    private val _month = MutableStateFlow("")
    private val _year = MutableStateFlow("")

    fun mockApiCall(detail: Detail) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            delay(2000)
            detailRepository.saveDetail(detail)
            emit(Resource.success(data = null))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun emitPan(pan: String) {
        _pan.value = pan
    }

    fun emitDay(day: String) {
        _day.value = day
    }

    fun emitMonth(month: String) {
        _month.value = month
    }

    fun emitYear(year: String) {
        _year.value = year
    }

    val isButtonEnabled: Flow<Boolean> = combine(_pan, _day, _month, _year) { pan, day, month, year ->
        val regex = "[A-Z]{5}[0-9]{4}[A-Z]".toRegex()
        return@combine regex.containsMatchIn(pan) and validateDate(day, month, year)
    }

    private fun validateDate(day: String, month: String, year: String): Boolean {
        return try {
            LocalDate.parse("$year$month$day", DateTimeFormatter.BASIC_ISO_DATE)
            true
        } catch (e: DateTimeParseException) {
            return false
        }
    }
}
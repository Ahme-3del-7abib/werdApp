package com.ahmed.a.habib.werdapp.features.counter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.a.habib.domain.counter.useCases.CounterUseCases
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val useCases: CounterUseCases
) : ViewModel() {

    private val sendIntend: MutableSharedFlow<CounterIntents> = MutableSharedFlow()

    private val _result: SingleMutableLiveData<CounterViewStates> = SingleMutableLiveData()
    val result: SingleMutableLiveData<CounterViewStates> get() = _result

    fun sendIntend(intent: CounterIntents) = viewModelScope.launch {
        sendIntend.emit(intent)
    }

    init {
        viewModelScope.launch {
            sendIntend.collectLatest {
                when (it) {
                    is CounterIntents.GetWerdById -> getWerdById(it.id)

                    is CounterIntents.UpdateCurrentCount -> updateCurrentWerd(it.count, it.id)

                    is CounterIntents.UpdateMaxCount -> updateMaxCount(it.count, it.id)

                    is CounterIntents.ValidateInput -> validateInput(it.currentCount, it.maxCount)
                }
            }
        }
    }

    private fun validateInput(currentCount: Float, maxCount: String) = viewModelScope.launch {
        if (maxCount.isEmpty()) {
            _result.value = CounterViewStates.ErrorId(R.string.must_enter_werd_count)
        } else if (maxCount.toInt() < currentCount.toInt()) {
            _result.value =
                CounterViewStates.ErrorId(R.string.max_count_must_be_more_than_current)
        } else {
            _result.value = CounterViewStates.InputIsValid(maxCount.toFloat())
        }
    }

    private fun updateMaxCount(count: Float, id: Int) = viewModelScope.launch {
        try {
            useCases.updateMaxCount(count, id)
            _result.value = CounterViewStates.Success
        } catch (e: Exception) {
            _result.value = CounterViewStates.ErrorMsg(e.message.orEmpty())
        }
    }

    private fun updateCurrentWerd(count: Float, id: Int) = viewModelScope.launch {
        try {
            useCases.updateCurrentCount(count, id)
            _result.value = CounterViewStates.Success
        } catch (e: Exception) {
            _result.value = CounterViewStates.ErrorMsg(e.message.orEmpty())
        }
    }

    private fun getWerdById(id: Int) = viewModelScope.launch {
        try {
            _result.value = CounterViewStates.GotWerd(useCases.getWerdById(id))
        } catch (e: Exception) {
            _result.value = CounterViewStates.ErrorMsg(e.message.orEmpty())
        }
    }
}
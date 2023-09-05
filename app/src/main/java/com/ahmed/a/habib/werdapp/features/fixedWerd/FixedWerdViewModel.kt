package com.ahmed.a.habib.werdapp.features.fixedWerd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.domain.fixedWerd.useCases.FixedWerdUseCases
import com.ahmed.a.habib.werdapp.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixedWerdViewModel @Inject constructor(
    private val useCases: FixedWerdUseCases
) : ViewModel() {

    private val sendIntend: MutableSharedFlow<FixedWerdIntents> = MutableSharedFlow()

    fun sendIntend(intent: FixedWerdIntents) = viewModelScope.launch {
        sendIntend.emit(intent)
    }

    private val _result: SingleMutableLiveData<FixedWerdViewStates> = SingleMutableLiveData()
    val result: SingleMutableLiveData<FixedWerdViewStates> get() = _result

    init {
        viewModelScope.launch {
            sendIntend.collectLatest {
                when (it) {
                    is FixedWerdIntents.GetAllFixedWerds -> getAllFixedWerds()

                    is FixedWerdIntents.InsertFixedWerd -> insertWerd(it.fixedWerdList)
                }
            }
        }
    }

    private fun insertWerd(werdList: List<WerdDataItems>) = viewModelScope.launch {
        try {
            for (item in werdList) {
                useCases.insertFixedWerd(item)
            }
            _result.value = FixedWerdViewStates.SuccessInsertFixedWerd
        } catch (e: Exception) {

            _result.value = FixedWerdViewStates.ERROR(e.message.orEmpty())
        }
    }

    private fun getAllFixedWerds() = viewModelScope.launch {
        try {
            _result.value = FixedWerdViewStates.SuccessGetAllWerds(useCases.getAllFixedWerds())
        } catch (e: Exception) {
            _result.value = FixedWerdViewStates.ERROR(e.message.orEmpty())
        }
    }
}
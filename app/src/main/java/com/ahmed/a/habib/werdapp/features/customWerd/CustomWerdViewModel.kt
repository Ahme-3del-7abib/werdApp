package com.ahmed.a.habib.werdapp.features.customWerd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.a.habib.domain.customWerd.useCases.CustomWerdUseCases
import com.ahmed.a.habib.domain.fixedWerd.model.WerdDataItems
import com.ahmed.a.habib.werdapp.R
import com.ahmed.a.habib.werdapp.utils.SingleMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CustomWerdViewModel @Inject constructor(private val useCase: CustomWerdUseCases) :
    ViewModel() {

    private val sendIntend: MutableSharedFlow<CustomWerdIntents> = MutableSharedFlow()

    private val _result: SingleMutableLiveData<CustomWerdViewStates> = SingleMutableLiveData()
    val result: SingleMutableLiveData<CustomWerdViewStates> get() = _result

    fun sendIntend(intent: CustomWerdIntents) = viewModelScope.launch {
        sendIntend.emit(intent)
    }

    init {
        viewModelScope.launch {
            sendIntend.collectLatest {
                when (it) {
                    is CustomWerdIntents.Delete -> {
                        delete(it.id)
                    }

                    is CustomWerdIntents.Insert -> {
                        insert(it.werd)
                    }

                    is CustomWerdIntents.Update -> {
                        update(it.werd)
                    }

                    is CustomWerdIntents.GetAllWerds -> {
                        getAllWerds()
                    }

                    is CustomWerdIntents.ValidateInput -> {
                        validateInput(it.nameEd, it.contentED, it.maxCountED, it.currentCount)
                    }
                }
            }
        }
    }

    private fun insert(item: WerdDataItems) = viewModelScope.launch {
        try {
            useCase.insertCustomWerd(item)
            _result.value = CustomWerdViewStates.SUCCESS
        } catch (e: Exception) {
            _result.value = CustomWerdViewStates.ErrorMsg(e.message.orEmpty())
        }
    }

    private fun update(item: WerdDataItems) = viewModelScope.launch {
        try {
            useCase.updateCustomWerd(item)
            _result.value = CustomWerdViewStates.SUCCESS
        } catch (e: Exception) {
            _result.value = CustomWerdViewStates.ErrorMsg(e.message.orEmpty())
        }
    }

    private fun delete(id: Int?) = viewModelScope.launch {
        try {
            if (id == null) {
                _result.value = CustomWerdViewStates.ErrorId(R.string.failed_delete_item)
            } else {
                useCase.delete(id)
                _result.value = CustomWerdViewStates.SUCCESS
            }
        } catch (e: Exception) {
            _result.value = CustomWerdViewStates.ErrorMsg(e.message.orEmpty())
        }
    }

    private fun getAllWerds() = viewModelScope.launch {
        try {
            _result.value = CustomWerdViewStates.GetAllCustomWerds(useCase.getAllCustomWerds())
        } catch (e: Exception) {
            _result.value = CustomWerdViewStates.ErrorMsg(e.message.orEmpty())
        }
    }

    private fun validateInput(
        name: EditText,
        content: EditText,
        count: EditText,
        currentCount: Float?
    ) = viewModelScope.launch {
        if (name.text.isEmpty() && content.text.isEmpty() && count.text.isEmpty()) {
            _result.value =
                CustomWerdViewStates.ErrorId(R.string.must_enter_werd_count_and_name_and_content)
        } else if (name.text.isEmpty()) {
            _result.value = CustomWerdViewStates.ErrorId(R.string.must_enter_werd_name)
        } else if (content.text.isEmpty()) {
            _result.value = CustomWerdViewStates.ErrorId(R.string.must_enter_werd_content)
        } else if (count.text.isEmpty()) {
            _result.value = CustomWerdViewStates.ErrorId(R.string.must_enter_werd_count)
        } else if (currentCount != null
            && (count.text.toString().toInt() < currentCount.toInt())
        ) {
            _result.value =
                CustomWerdViewStates.ErrorId(R.string.max_count_must_be_more_than_current)
        } else {
            _result.value = CustomWerdViewStates.InputIsValid
        }
    }
}
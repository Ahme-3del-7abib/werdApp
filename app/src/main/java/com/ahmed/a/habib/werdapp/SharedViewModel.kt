package com.ahmed.a.habib.werdapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.a.habib.werdapp.utils.SingleMutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _counterFragmentIsOpen: SingleMutableLiveData<Boolean> = SingleMutableLiveData()
    val counterFragmentIsOpen: SingleMutableLiveData<Boolean> = _counterFragmentIsOpen

    fun setCounterFragmentIsOpen(value: Boolean) {
        _counterFragmentIsOpen.value = value
    }

    private val _getCurrentVPPosition: SingleMutableLiveData<Int> = SingleMutableLiveData()
    val getCurrentVPPosition: SingleMutableLiveData<Int> = _getCurrentVPPosition

    fun setCurrentVPItem(page: Int) {
        _getCurrentVPPosition.value = page
    }

    private val _backViewPager: SingleMutableLiveData<Boolean> = SingleMutableLiveData()
    val backViewPager: SingleMutableLiveData<Boolean> = _backViewPager

    fun backVPager() {
        _backViewPager.value = true
    }
}
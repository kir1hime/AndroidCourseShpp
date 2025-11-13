package com.example.androidcourseshpp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.ui.screens.auth.signup.SignUpContract.Effect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewEffect
interface ViewState

abstract class BaseViewModel<UIEvent : ViewEvent, UIEffect : ViewEffect, UIState : ViewState> : ViewModel() {

    protected abstract fun initState(): UIState
    protected abstract fun handleEvent(event: UIEvent)
    private val _state: MutableStateFlow<UIState> = MutableStateFlow(initState())
    val state: StateFlow<UIState> = _state

    private val _effect: Channel<UIEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private val _event: MutableSharedFlow<UIEvent> = MutableSharedFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect { event ->
                handleEvent(event)
            }
        }
    }

   protected fun setState(modifier: UIState.() -> UIState) {
        _state.update { state ->
            state.modifier()
        }
    }

    fun setEvent(event: UIEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun setEffect(effect : UIEffect){
        viewModelScope.launch {
            _effect.send(effect)
        }
    }



}


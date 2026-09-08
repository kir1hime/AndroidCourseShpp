package com.example.androidcourseshpp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.RootError
import com.example.androidcourseshpp.domain.utils.UnknownError
import kotlinx.coroutines.CoroutineExceptionHandler
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

abstract class BaseViewModel<UIEvent : ViewEvent, UIEffect : ViewEffect, UIState : ViewState> :
    ViewModel() {

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

    protected fun setEffect(effect: UIEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    protected fun <T> executeUseCase(
        toExecute: suspend () -> Result<T, RootError>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((RootError) -> Unit)? = null,
        onNetworkError: ((DataError.NetworkError) -> Unit)? = null,
        onLocalError: (() -> Unit)? = null,
        onUnknownError: (() -> Unit)? = null,
        finally: (() -> Unit)? = null,
        coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ ->
            onError?.invoke(UnknownError)
        }
    ) {

        viewModelScope.launch(coroutineExceptionHandler) {
            when (val result = toExecute()) {
                is Result.Success -> onSuccess?.invoke(result.data)
                is Result.Error<*> -> {
                    when (result.error) {
                        is DataError.NetworkError -> onNetworkError.also { networkErrorHandler ->
                            if (networkErrorHandler != null) {
                                networkErrorHandler.invoke(result.error)
                            } else {
                                onError?.invoke(result.error)
                            }
                        }

                        is DataError.LocalError -> onLocalError.also { localErrorHandler ->
                            if (localErrorHandler != null) {
                                localErrorHandler.invoke()
                            } else {
                                onError?.invoke(result.error)
                            }
                        }

                        else -> {
                            onUnknownError?.invoke()
                        }
                    }
                }
            }
            finally?.invoke()
        }
    }
}
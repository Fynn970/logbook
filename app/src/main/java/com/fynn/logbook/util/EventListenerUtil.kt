package com.fynn.logbook.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

//监听一个属性
fun <T, A> Flow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@observeState.map {
                StateTuple1(prop1.get(it))
            }.distinctUntilChanged().collect { (a) ->
                action.invoke(a)
            }
        }
    }
}

fun <T, A> SharedFlow<T>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    scope: CoroutineScope,
    prop1: KProperty1<T, A>,
    action: (A) -> Unit
) {
    this@observeEvent
        .flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        ).map {
            StateTuple1(prop1.get(it))
        }.onEach { (a: A) ->
            action.invoke(a)
        }.launchIn(scope)
}

//监听两个属性
fun <T, A, B> Flow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    prop1: KProperty1<T, A>,
    prop2: KProperty1<T, B>,
    action: (A, B) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@observeState.map {
                StateTuple2(prop1.get(it), prop2.get(it))
            }.distinctUntilChanged().collect { (a, b) ->
                action.invoke(a, b)
            }
        }
    }
}

fun <T> Flow<T>.observeState(
    lifecycleOwner: LifecycleOwner,
    action: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@observeState.map {
                it
            }.distinctUntilChanged().collect { it ->
                action.invoke(it)
            }
        }
    }
}


internal data class StateTuple1<A>(val a: A)
internal data class StateTuple2<A, B>(val a: A, val b: B)
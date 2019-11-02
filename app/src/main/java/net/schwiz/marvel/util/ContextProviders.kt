package net.schwiz.marvel.util

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class ContextProviders(
    val main : CoroutineContext = Dispatchers.Main,
    val io : CoroutineContext = Dispatchers.IO
)
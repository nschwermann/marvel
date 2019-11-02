package net.schwiz.marvel.core

sealed class Option<out T>{
    companion object{
        fun <T> something(v : T) = Some(v)
        fun <T> nothing() = None
    }
    data class Some<out T>(val value : T) :Option<T>()
    object None : Option<Nothing>()

    fun <R> map(mapper : (T) -> R) : Option<R>{
        return when(this){
            is Some -> Some(mapper(value))
            else -> None
        }
    }

    fun <R> flatMap(mapper : (T) -> Option<R>) :Option<R> {
        return when(this){
            is Some -> mapper(value)
            else -> None
        }
    }

    fun bind() : T? {
        return when(this){
            is Some -> value
            else -> null
        }
    }
}
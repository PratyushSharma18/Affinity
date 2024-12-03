package com.pratyushvkp.affinity.data

open class Event<out T>(private val content: T) {
    var hasBeenHnadled = false
    private set

    fun getContentOrNull(): T?{
        return if (hasBeenHnadled)
            null
        else {
            hasBeenHnadled = true
            content
        }
    }
}
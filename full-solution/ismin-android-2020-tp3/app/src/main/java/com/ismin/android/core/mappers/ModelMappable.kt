package com.ismin.android.core.mappers

interface ModelMappable<out R> {
    fun asModel(): R
}

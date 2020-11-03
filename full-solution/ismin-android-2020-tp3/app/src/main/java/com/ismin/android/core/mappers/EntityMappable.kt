package com.ismin.android.core.mappers

interface EntityMappable<out R> {
    fun asEntity(): R
}

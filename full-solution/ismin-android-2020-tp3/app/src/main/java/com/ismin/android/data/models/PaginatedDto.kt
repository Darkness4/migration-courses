package com.ismin.android.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedDto<T>(
    val _links: Links,
    val limit: Int,
    val results: List<T>,
    val size: Int,
    val start: Int
) {
    @Serializable
    data class Links(
        val next: String,
        val prev: String,
        val self: String
    )
}

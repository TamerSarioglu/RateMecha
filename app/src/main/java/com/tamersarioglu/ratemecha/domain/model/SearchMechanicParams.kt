package com.tamersarioglu.ratemecha.domain.model

data class SearchMechanicParams(
    val query: String? = null,
    val city: String? = null,
    val state: String? = null,
    val specialty: String? = null
)
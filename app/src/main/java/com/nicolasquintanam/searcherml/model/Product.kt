package com.nicolasquintanam.searcherml.model

data class Product(
    val id: String,
    val name: String,
    var attributeName: String? = null,
    var attributeValue: String? = null
)
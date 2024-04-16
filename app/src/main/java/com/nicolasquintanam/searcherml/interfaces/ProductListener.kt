package com.nicolasquintanam.searcherml.interfaces

import com.nicolasquintanam.searcherml.model.Product

interface ProductListener {
    fun onProductsReceived(products: List<Product>)
    fun onFailure(message: String)
}
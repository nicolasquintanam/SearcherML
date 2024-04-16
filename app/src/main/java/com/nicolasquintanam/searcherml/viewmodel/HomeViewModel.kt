package com.nicolasquintanam.searcherml.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nicolasquintanam.searcherml.interfaces.ProductListener
import com.nicolasquintanam.searcherml.model.Product
import com.nicolasquintanam.searcherml.repository.ProductRepository


class HomeViewModel : ViewModel() {
    private val productRepository = ProductRepository()
    private val _productList = MutableLiveData<List<Product>>()
    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    val productList: LiveData<List<Product>>
        get() = _productList

    fun searchProducts(text: String){
        val listener = object : ProductListener {
            override fun onProductsReceived(products: List<Product>) {
                _productList.postValue(products)
            }

            override fun onFailure(message: String) {
                _errorLiveData.postValue(message)
            }
        }
        productRepository.getProductsList(text, listener)
    }


}
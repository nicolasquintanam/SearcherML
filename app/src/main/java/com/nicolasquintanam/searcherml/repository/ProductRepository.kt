package com.nicolasquintanam.searcherml.repository

import android.util.Log
import com.nicolasquintanam.searcherml.interfaces.ProductListener
import com.nicolasquintanam.searcherml.model.Product
import java.io.IOException
import okhttp3.*
import org.json.JSONObject

class ProductRepository {
    fun getProductsList(text: String, listener: ProductListener): List<Product>{
            val productList = mutableListOf<Product>()
            val url = "https://api.mercadolibre.com/products/search?status=active&site_id=MLC&q=$text"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("Error", "${e.message}")
                    listener.onFailure("Ocurrió un error, por favor intenta nuevamente")
                }
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    val jsonObject = JSONObject(responseBody)
                    val resultsArray = jsonObject.getJSONArray("results")

                    for (i in 0 until resultsArray.length()) {
                        val resultObject = resultsArray.getJSONObject(i)
                        val name = resultObject.getString("name")
                        val id = resultObject.getString("id")
                        val attributesArray = resultObject.getJSONArray("attributes")
                        val createdProduct = Product(id, name)
                        if (attributesArray.length() > 0) {
                            val firstAttribute = attributesArray.getJSONObject(0)
                            val attributeName = firstAttribute.getString("name")
                            val attributeValueName = firstAttribute.getString("value_name")

                            createdProduct.attributeName = attributeName
                            createdProduct.attributeValue = attributeValueName
                        }
                        productList.add(createdProduct)
                    }
                    listener.onProductsReceived(productList)
                }})

        return productList
    }

}
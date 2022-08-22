package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentAddProductBinding
import com.example.coffeshop.domain.model.Product
import com.example.coffeshop.domain.model.service
import com.example.coffeshop.domain.model.token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProduct : Fragment() {

    fun nav() {
        NavHostFragment.findNavController(this).navigate(R.id.action_addProduct_to_homepage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddProductBinding>(
            inflater, R.layout.fragment_add_product, container, false
        )
        val name = binding.etProductName
        val description = binding.etDescription
        val price = binding.etPrice
        val image = binding.etImage
        val category = binding.etCategory
        binding.add.setOnClickListener {
            if (name.text.toString() != "" && description.text.toString() != "" && price.text.toString() != "" && image.text.toString() != "" && category.text.toString() != "") {
                val product = Product(
                    name.text.toString(),
                    description.text.toString(),
                    price.text.toString().toDouble(),
                    image.text.toString(),
                    category.text.toString()
                )
                service.addProduct(token, product).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.code() == 200) {
                            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT)
                                .show()
                            nav()
                        } else {
                            Toast.makeText(context, "Can't Add product", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(context, "Internet Problem", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            } else
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
        }
        return binding.root
    }


}
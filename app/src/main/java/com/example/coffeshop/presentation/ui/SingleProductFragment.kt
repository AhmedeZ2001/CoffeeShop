package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentSingleProductBinding
import com.example.coffeshop.domain.model.service
import com.example.coffeshop.domain.model.token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class single_product : Fragment() {
    fun nav(){
        NavHostFragment.findNavController(this).navigate(R.id.action_single_product_to_homepage)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSingleProductBinding>(
            inflater, R.layout.fragment_single_product, container, false
        )
        binding.tvCoffeeName.text=arguments?.getString("coffee_name")
        val value=arguments?.getString("desc")
        val price=arguments?.get("price").toString()
        val img=arguments?.get("image").toString()
        val id =arguments?.get("id").toString()
        Glide.with(binding.ivProduct).load(img).into(binding.ivProduct)
        binding.tvPrice.text=price
        binding.desc.text=value
        binding.btnAdd.setOnClickListener {
            var quantity=binding.Quantity.text.toString().toInt()
            quantity++
            binding.Quantity.text=quantity.toString()
        }
        binding.btnRemove.setOnClickListener {
            var quantity = binding.Quantity.text.toString().toInt()
            if(quantity>0)
                quantity--
            binding.Quantity.text=quantity.toString()
        }
        binding.remove.setOnClickListener {
            service.delete(id.toLong(), token).enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if(response.code()==200)
                    {
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT)
                            .show()
                        nav()
                    }
                    else if (response.code()==500)
                    {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else
                        Toast.makeText(context, "${response.code()}", Toast.LENGTH_SHORT)
                            .show()
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }

        return binding.root
    }

}
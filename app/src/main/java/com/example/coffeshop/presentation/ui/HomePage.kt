package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentHomepageBinding
import com.example.coffeshop.domain.model.Coffee
import com.example.coffeshop.domain.model.service
import com.example.coffeshop.domain.model.token
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomePage : Fragment(), Adapter.ItemClick {
    var coffeeList: MutableList<Coffee> = ArrayList()
    lateinit var hotCoffeeList: List<Coffee>
    lateinit var iceCoffeeList: List<Coffee>
    lateinit var listAdapter: Adapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomepageBinding>(
            inflater, R.layout.fragment_homepage, container, false
        )
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    val name = arguments?.get("name").toString()
                    val email = arguments?.get("email").toString()
                    val data = bundleOf(
                        "name" to name,
                        "email" to email
                    )
                    NavHostFragment.findNavController(this).navigate(R.id.profile, data)
                }
                R.id.addProduct -> NavHostFragment.findNavController(this).navigate(R.id.addProduct)
            }
            true
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val price = this.coffeeList[position].price.toString()
        val img = this.coffeeList[position].image
        val id = this.coffeeList[position].id
        val name = bundleOf(
            "coffee_name" to this.coffeeList[position].name,
            "desc" to this.coffeeList[position].description,
            "price" to price,
            "image" to img,
            "id" to id
        )

        NavHostFragment.findNavController(this)
            .navigate(R.id.action_homepage_to_single_product, name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.productRv)
        getProducts(this)
        getHotProducts(this)
    }

    private fun getProducts(homePage: HomePage) {
        service.getAllIcedProducts(token).enqueue(object : Callback<MutableList<Coffee>> {
            override fun onResponse(
                call: Call<MutableList<Coffee>>,
                response: Response<MutableList<Coffee>>
            ) {
                iceCoffeeList = response?.body()!!
                listAdapter = Adapter(coffeeList, homePage)
                recyclerView.adapter = listAdapter
                iceCoffeeList.stream().forEach { coffee ->
                    if(! coffeeList.contains(coffee))
                    coffeeList.add(coffee)
                }
            }

            override fun onFailure(call: Call<MutableList<Coffee>>, t: Throwable) {
                Log.d("????", "Failed")
            }
        })
    }


    private fun getHotProducts(homePage: HomePage) {
        service.getAllHotProducts(token).enqueue(object : Callback<MutableList<Coffee>> {
            override fun onResponse(
                call: Call<MutableList<Coffee>>,
                response: Response<MutableList<Coffee>>
            ) {
                hotCoffeeList = response?.body()!!
                listAdapter = Adapter(coffeeList, homePage)
                recyclerView.adapter = listAdapter
                hotCoffeeList.stream().forEach { coffee ->
                    if(! coffeeList.contains(coffee))
                        coffeeList.add(coffee)
                }

            }

            override fun onFailure(call: Call<MutableList<Coffee>>, t: Throwable) {
                Log.d("????", "fail")
            }

        })
    }

}
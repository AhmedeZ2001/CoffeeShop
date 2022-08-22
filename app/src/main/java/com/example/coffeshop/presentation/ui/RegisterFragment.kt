package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentRegisterBinding
import com.example.coffeshop.domain.model.FullUser
import com.example.coffeshop.domain.model.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RegisterFragment : Fragment() {

    fun nav(){
        NavHostFragment.findNavController(this).navigate(R.id.action_register_to_login)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(
            inflater, R.layout.fragment_register, container, false
        )
        binding.btnSingUp.setOnClickListener {
            val name:String =binding.etName.text.toString()
            val email: String = binding.etEmail.text.toString()
            val pass: String = binding.etPassword.text.toString()
            if (email != "" && pass != "" && name !="") {
                val newFullUser=FullUser(name,email,pass)
                service.register(newFullUser).enqueue(object:Callback<Unit>
                {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if(response.code()==200)
                        {
                            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT)
                                .show()
                            nav()
                        }
                        else if(response.code()==400){
                            Toast.makeText(context, "Email is already taken", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Toast.makeText(context, "Internet Problem", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            }
        }
        return binding.root
    }

}
/*private fun makeRequest()
{
    val api = Retrofit.Builder()
        .baseUrl("https://murmuring-temple-54993.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(userInterface::class.java)

    val requestModel = User("username123", "hello123","123")

    api.register(requestModel)?.enqueue(
        object : Callback<ResponseModel> {
            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) { Toast.makeText(this@RegisterActivity,"Register done !",Toast.LENGTH_LONG).show()
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,t.toString(),Toast.LENGTH_LONG).show()
            }

        }
    )
}*/

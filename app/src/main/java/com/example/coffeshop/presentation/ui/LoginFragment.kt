package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentLoginBinding
import com.example.coffeshop.domain.model.LoginResponse
import com.example.coffeshop.domain.model.User
import com.example.coffeshop.domain.model.service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater, R.layout.fragment_login, container, false
        )
        binding.btnRegister.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_login_to_register)
        }
        fun nav(data:Bundle)
        {

            NavHostFragment.findNavController(this).navigate(R.id.action_login_to_homepage,data)
        }

        binding.btnLogin.setOnClickListener {
            val email:String=binding.etEmail.text.toString()
            val pass:String=binding.etPassword.text.toString()
            if ( email != "" && pass !="") {
                val newUser=User(email,pass)

                service.login(newUser).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if(response.code()==200)
                        {
                            val name=response.body()?.name
                            val email=response.body()?.email
                            val data= bundleOf("name" to name,
                                "email" to email)
                            nav(data)
                            Toast.makeText(context, "Hello $name", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else
                        {
                            Toast.makeText(context, "Wrong email or password", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context, "Internet Problem", Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            }
            else
            {
                Toast.makeText(context, "Please Enter your email and password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return  binding.root
    }




}
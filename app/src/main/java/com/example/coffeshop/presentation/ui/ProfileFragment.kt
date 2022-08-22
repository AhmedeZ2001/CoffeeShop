package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentProfileBinding


class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile, container, false
        )
        val name=arguments?.getString("name")
        val email=arguments?.getString("email")
        binding.tvUserName.text=binding.tvUserName.text.toString() + ":" +name
        binding.tvEmail.text=binding.tvEmail.text.toString() + ":" +email
        binding.btnEdit.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_profile_to_login)
        }

        return binding.root
    }

}



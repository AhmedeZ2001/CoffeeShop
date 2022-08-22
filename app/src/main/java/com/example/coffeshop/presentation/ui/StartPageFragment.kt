package com.example.coffeshop.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentStartPageBinding

class start_page : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStartPageBinding>(
            inflater, R.layout.fragment_start_page, container, false
        )
        binding.btnStart.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_start_page_to_login)
        }

        return binding.root
    }


}
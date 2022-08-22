package com.example.coffeshop.domain.model

data class LoginResponse(
    val name :String?=null,
    val email :String?=null,
    val token:String?=null
)

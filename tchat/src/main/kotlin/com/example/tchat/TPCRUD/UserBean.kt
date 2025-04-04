package com.example.tchat.TPCRUD

import jakarta.validation.constraints.Size

data class UserBean(var id: Long? = null, var login: String = "",
                    @field:Size(min = 3, message = "Il faut au moins 3 caractères") var password: String = "")
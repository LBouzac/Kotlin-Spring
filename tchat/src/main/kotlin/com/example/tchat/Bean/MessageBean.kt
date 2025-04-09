package com.example.tchat.Bean

import io.swagger.v3.oas.annotations.media.Schema

//@Schema :  pour la documentation avec Swagger

@Schema(description = "Représente un message dans le tchat")
data class MessageBean(
    @Schema(description = "Pseudo de l'utilisateur", example = "toto")
    var pseudo: String = "",

    var message : String = "")

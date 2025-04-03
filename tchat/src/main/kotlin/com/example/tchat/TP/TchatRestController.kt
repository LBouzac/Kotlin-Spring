package com.example.tchat.TP

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Gestion d'un tchat", description = "Fait pour ecrire un message, et le lire")
@RequestMapping("/tchat")
class TchatRestController {

    // Liste pour stocker les messages
    private val messages = ArrayList<MessageBean>()

    @Operation(
        summary = "Créer un message",
        description = "Crée un message et l'ajoute à la liste des messages",
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Message créé avec succès",
            content = arrayOf(
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = MessageBean::class)
                )
            )
        ),
        ApiResponse(responseCode = "400", description = "Requête invalide"),
        ApiResponse(responseCode = "500", description = "Erreur serveur")
    ])
    //http://localhost:8080/tchat/saveMessage
    @PostMapping("/saveMessage")
    fun saveMessage(@RequestBody message: MessageBean) {
        messages.add(message)
        println("/saveMessage : $message")
    }

    @Operation(
        summary = "Récupérer tous les messages",
        description = "Retourne les 10 derniers messages enregistrés"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Liste des messages récupérée avec succès",
            content = arrayOf(
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArrayList::class)
                )
            )
        ),
        ApiResponse(responseCode = "500", description = "Erreur serveur")
    ])

    //http://localhost:8080/tchat/allMessages
    @GetMapping("/allMessages")
    fun allMessages(): List<MessageBean> {
        return messages.takeLast(10)
    }

    @Operation(
        summary = "Filtrer les messages",
        description = "Filtre les messages par nom"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Liste des messages filtrée avec succès",
            content = arrayOf(
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ArrayList::class)
                )
            )
        ),
        ApiResponse(responseCode = "400", description = "Paramètres de filtre invalides"),
        ApiResponse(responseCode = "500", description = "Erreur serveur")
    ])
    //http://localhost:8080/tchat/filter?name=John
    @GetMapping("/filter")
    fun filter(@RequestParam name: String): List<MessageBean> {
        return messages.filter { it.pseudo == name }.takeLast(10)
    }

}
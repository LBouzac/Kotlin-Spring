package com.example.tchat.TPCRUD

import com.example.tchat.TP.MessageBean
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@Tag(name = "CRUD", description = "Fait pour gérer les utilisateurs")
class UserRestController {

    @Operation(
        summary = "Créer un utilisateur",
        description = "Ajoute l'utilisateur reçu (sans id) à l'aide de UserService et retourne l'utilisateur créé avec l'id créé",
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Création de l'utilisateur réussie",
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
    // Post un utilisateur
    @PostMapping
    fun post(@Valid @RequestBody user: UserBean): ResponseEntity<UserBean> {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserService.save(user))
    }


    @Operation(
        summary = "Liste d'utilisateurs",
        description = "Liste tous les utilisateurs ou l'id de l'utilisateur si précisé",
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Liste récupérée avec succès",
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
    // GET tous les utilisateurs ou un utilisateur spécifique par ID

    @GetMapping("/{id}")
    fun get(@RequestParam(required = false) id: Long?): ResponseEntity<Any> {
        return if (id != null) {
            // Si un ID est fourni, retourner l'utilisateur spécifique
            ResponseEntity.status(HttpStatus.OK).body(UserService.findById(id))
        } else {
            // Si aucun ID n'est fourni, retourner tous les utilisateurs
            ResponseEntity.status(HttpStatus.OK).body(UserService.load())
        }
    }

    @Operation(
        summary = "Modifier un utilisateur",
        description = "Modif",
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Modifier avec succès",
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
    // PUT un utilisateur
    @PutMapping("/{id}")
    fun put(@Valid @RequestBody user: UserBean): ResponseEntity<UserBean> {
        return ResponseEntity.status(HttpStatus.OK).body(UserService.save(user))
    }

    @Operation(
        summary = "Supprimer un utilisateur",
        description = "Supprime l'utilisateur avec l'ID spécifié",
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Suppression avec succès",
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
    // DELETE un utilisateur
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        try {
            val result = UserService.deleteById(id)
            return ResponseEntity.status(HttpStatus.OK).body(result)
        } catch (e: NoSuchElementException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf("error" to "Utilisateur non trouvé avec l'id: $id"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapOf("error" to "Erreur lors de la suppression: ${e.message}"))
        }
    }
}
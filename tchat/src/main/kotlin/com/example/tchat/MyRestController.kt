package com.example.tchat

import com.example.tchat.TP.MessageBean
import com.example.tchat.TP.StudentBean
import com.example.tchat.TPCRUD.UserBean
import com.example.tchat.TPCRUD.UserService
import com.example.tchat.TPbdd.TeacherBean
import com.example.tchat.TPbdd.TeacherService
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
@RequestMapping("/api")
class MyRestController {

    //http://localhost:8080/test
    @GetMapping("/test")
    fun testMethode(): String {
        println("/test")
        return "helloWorld"
    }

    //http://localhost:8080/getStudent
    @GetMapping("/getStudent")
    fun getStudent(): StudentBean {
        println("/getStudent")
        var student =  StudentBean("toto", 12)

        return student
    }

    //Version avec valeur par défaut
    //http://localhost:8080/createStudent?name=aaa¬e=15
    @GetMapping("/createStudent")
    fun createStudent(name: String = "abc", note: Int): StudentBean? {
        //name contiendra bob
        //note contiendra 12
        println("/createStudent : name=$name note=$note")

        return StudentBean(name, note)
    }

    //Version avec @RequestParam
    //http://localhost:8080/createStudentV2?name=aaa¬e=15
    @GetMapping("/createStudentV2")
    fun createStudentV2(@RequestParam(value = "name", defaultValue = "abc") p1: String, note: Int): StudentBean? {
        //p1 contiendra bob
        //note contiendra 12
        println("/createStudentV2 : p1=$p1 note=$note")

        return StudentBean(p1, note)
    }

    //http://localhost:8080/receiveStudent
    @PostMapping("/receiveStudent")
    fun receiveStudent(@RequestBody student: StudentBean): StudentBean {
        println("/receiveStudent : $student")

        //traitement, mettre en base…
        //Retourner d'autres données
        return student
    }

    //http://localhost:8080/increment
    @PostMapping("/increment")
    fun increment(@RequestBody student: StudentBean): StudentBean {
        println("/increment : $student")

        //traitement, mettre en base…
        //Retourner d'autres données
        return StudentBean(student.name, student.note + 1)
    }


    //On met à null s'il n'y a pas la valeur
    //http://localhost:8080/max?p1=5&p2=6
    @GetMapping("/max")
    fun max(p1: Int?, p2: Int?): Int? {
        //p1 contiendra bob
        //note contiendra 12
        println("/max : p1=$p1 p2=$p2")

        return if(p1 == null) p2 else if(p2 == null) p1 else Math.max(p1,p2)
    }

    //on met des String pour gérer les valeurs non int
    //http://localhost:8080/max2?p1=5&p2=6
    @GetMapping("/max2")
    fun max2(p1: String?, p2: String?): Int? {
        //p1 contiendra bob
        //note contiendra 12
        println("/max2 : p1=$p1 p2=$p2")

        var p1Int = p1?.toIntOrNull()
        var p2Int = p2?.toIntOrNull()

        return if(p1Int == null) p2Int else if(p2Int == null) p1Int else Math.max(p1Int, p2Int)
    }

    //http://localhost:8080/Boulangerie?nbCroissant=3&nbSandwich=1
    @GetMapping("/Boulangerie")
    fun boulangerie(@RequestParam(value = "nbCroissant", defaultValue = "0") nbCroissant: Int,
                    @RequestParam(value = "prixCroissant", defaultValue = "0.95") prixCroissant: Double,
                    @RequestParam(value = "prixSandwich", defaultValue = "8.00") prixSandwich: Double,
                    @RequestParam(value = "nbSandwich", defaultValue = "0") nbSandwich: Int): String {
        return "Boulangerie : Croissant = $nbCroissant Sandwich = $nbSandwich pour un total de ${String.format("%.2f",nbCroissant * prixCroissant + nbSandwich * prixSandwich)} euros"
    }
}

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

@RestController
@RequestMapping("/users")
class UserController {

    //http://localhost:8080/users
    @GetMapping
    fun getAllUsers() =  ResponseEntity.ok(UserService.load())

    //http://localhost:8080/users/1
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long?): ResponseEntity<UserBean> {
        val userBean = UserService.findById(id)
        return if (userBean != null) {
            ResponseEntity.ok(userBean)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //http://localhost:8080/users
    //{"login":"aaa", "password":"bbb"}
    @PostMapping
    fun createUser(@Valid @RequestBody user: UserBean?) = ResponseEntity(user?.let { UserService.save(it) }, HttpStatus.CREATED)

    //http://localhost:8080/users/1
    //{"login":"aaa", "password":"bbb"}
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long?,
        @Valid @RequestBody userDetails: UserBean?
    ): ResponseEntity<UserBean> {
        val user = UserService.findById(id)
        return if (user != null) {
            if (userDetails != null) {
                userDetails.id = id
            } //écrase celui reçu dans le JSON au cas ou
            if (userDetails != null) {
                UserService.save(userDetails)
            }
            ResponseEntity(userDetails, HttpStatus.OK)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //http://localhost:8080/users/1
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long?): ResponseEntity<Void> {
        return if (id?.let { UserService.deleteById(it) } == true) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        }
        else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}


@RestController //ou  @Controller
@RequestMapping("/bdd")
// http://localhost:8080/bdd/addTeacher?name=Vincent&code=1
class bddRestController(val teacherService : TeacherService) {
    @GetMapping("/addTeacher")
    fun addTeacher(name:String , code:Int) : List<TeacherBean> {
        println("/addTeacher name=$name code=code")

        teacherService.createTeacher(name, code)

        return  teacherService.getAll()
    }
}
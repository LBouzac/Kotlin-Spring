package com.example.tchat.restcontroller

import com.example.tchat.model.StudentBean
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
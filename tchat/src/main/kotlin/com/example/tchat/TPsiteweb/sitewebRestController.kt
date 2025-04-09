package com.example.tchat.TPsiteweb

import com.example.tchat.Bean.StudentBean
import com.example.tchat.Bean.UserBean
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class sitewebRestController {
    val studentList = ArrayList<StudentBean>()

    //http://localhost:8080/hello
    @GetMapping("/hello")
    fun testHello(model: Model): String {
        println("/hello")

        // Ajouter un String au modèle
        model.addAttribute("message", "Bonjour")


        // Ajouter une ArrayList de StudentBean
        studentList.add(StudentBean("Steven", 1))
        studentList.add(StudentBean("Vincent", 11))
        studentList.add(StudentBean("KK", 19))
        model.addAttribute("students", studentList)

        // Nom du fichier HTML que l'on souhaite afficher
        return "welcome"
    }

    @GetMapping("/add")
    fun testParam(
        @RequestParam(value = "name", defaultValue = "World") name: String,
        @RequestParam(value = "note", defaultValue = "10") note: Int,
        model: Model
    ): String {
        println("/add - Ajout de l'étudiant: $name avec note: $note")

        // Ajouter un String au modèle
        model.addAttribute("message", "Bonjour $name")

        // Ajouter le nouvel étudiant à la liste
        studentList.add(StudentBean(name = name, note = note))

        // Ajouter la liste mise à jour au modèle
        model.addAttribute("students", studentList)

        // Nom du fichier HTML que l'on souhaite afficher
        return "welcome"
    }

        //http://localhost:8080/login
        @GetMapping("/login")
        fun login(userBean: UserBean): String {
            println("/login")
            //Spring créera une instance de UserBean qu'il mettra dans le model
            return "login"
        }

        @PostMapping("/loginSubmit")
        fun loginSubmit(userBean: UserBean, redirect: RedirectAttributes): String {
            println("/loginSubmit : userBean=$userBean")

            try {
                if (userBean.login.isBlank()) {
                    throw Exception("Login manquant")
                }
                else if (userBean.password.isBlank()) {
                    throw Exception("Password manquant")
                }
                //Cas qui marche
                redirect.addFlashAttribute("userBean", userBean);
                return "redirect:userRegister" // Redirection sur /userRegister
            } catch (e:Exception) {
                e.printStackTrace()

                //Cas d'erreur
                //pour garder les données entrées dans le formulaire par l'utilisateur
                redirect.addFlashAttribute("userBean", userBean)
                //Pour transmettre le message d'erreur
                redirect.addFlashAttribute("errorMessage", e.message);
                return "redirect:login" //Redirige sur /login
            }
        }

        @GetMapping("/userRegister") //Affiche la page résultat
        fun userRegister(userBean: UserBean): String {
            println("/userRegister userBean=$userBean")

            return "login" //Lance login.html
        }
    }
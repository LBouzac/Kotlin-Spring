package com.example.tchat.TPbdd

import org.springframework.stereotype.Service

@Service
class TeacherService(val teacherRep:TeacherRepository) {

    //@Transactional si je souhaite le faire dans une transaction
    fun createTeacher(name:String?, code:Int) : TeacherBean {
        if(name.isNullOrBlank()){
            throw Exception("Name missing")
        }
        else if(code !in 1..10){
            throw Exception("Code incorrecte")
        }
        val teacher = TeacherBean(null, name, code, null)
        teacherRep.save(teacher)
        return teacher
    }

    fun getAll() = teacherRep.findAll()

}
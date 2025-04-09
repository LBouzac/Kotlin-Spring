package com.example.tchat.Repository

import com.example.tchat.Bean.TeacherBean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository                       //<Bean, Typage Id>
interface TeacherRepository : JpaRepository<TeacherBean, Long> {

}
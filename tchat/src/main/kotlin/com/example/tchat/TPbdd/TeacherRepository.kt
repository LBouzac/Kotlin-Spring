package com.example.tchat.TPbdd

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository                       //<Bean, Typage Id>
interface TeacherRepository : JpaRepository<TeacherBean, Long> {

}
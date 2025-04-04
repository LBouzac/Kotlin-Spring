package com.example.tchat.TPbdd

import jakarta.persistence.*

@Entity
@Table(name = "teacher")
data class TeacherBean(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = "",
    @Column(name = "code")
    var code: Int? = null,
    var sessionId : String? = null //Ira chercher session_id dans la table
)
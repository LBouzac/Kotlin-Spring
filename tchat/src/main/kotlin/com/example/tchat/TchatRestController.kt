package com.example.tchat

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tchat")
class TchatRestController {

    private val messages = ArrayList<MessageBean>()

    //http://localhost:8080/tchat/saveMessage
    @PostMapping("/saveMessage")
    fun saveMessage(@RequestBody message: MessageBean) {
        messages.add(message)
        println("/saveMessage : $message")
    }

    //http://localhost:8080/tchat/allMessages
    @GetMapping("/allMessages")
    fun allMessages(): List<MessageBean> {
        return messages.takeLast(10)
    }
}
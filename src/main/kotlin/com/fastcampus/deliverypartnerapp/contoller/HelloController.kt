package com.fastcampus.deliverypartnerapp.contoller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class HelloController {
    @GetMapping("/hello")
    fun hello(
        @RequestParam(name = "name", required = false, defaultValue = "Sonic") name: String?,
        model: Model
    ): String {
        model.addAttribute("name", name)
        return "hello"
    }
}
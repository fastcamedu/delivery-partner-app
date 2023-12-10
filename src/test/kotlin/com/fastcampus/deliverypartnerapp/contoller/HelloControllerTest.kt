package com.fastcampus.deliverypartnerapp.contoller

import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @Test
    fun hello() {
        mvc?.perform(MockMvcRequestBuilders.get("/hello?name=Sonic"))
            ?.andExpect(status().isOk)
            ?.andExpect(content().string(
                containsString("Hello World, Sonic")
            ))
    }
}

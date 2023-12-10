package com.fastcampus.deliverypartnerapp.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.util.function.Supplier

@Configuration
class RestTemplateConfig {

    companion object {
        private const val CONNECTION_TIMEOUT = 10000L
        private const val READ_TIMEOUT = 10000L
    }

    @Bean
    fun restTemplate(restTemplateBuilder: RestTemplateBuilder): RestTemplate {
        return restTemplateBuilder
            .requestFactory(
                Supplier {
                    BufferingClientHttpRequestFactory(
                        SimpleClientHttpRequestFactory()
                    )
                }
            )
            .setConnectTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
            .setReadTimeout(Duration.ofMillis(READ_TIMEOUT))
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // 3
            .build()
    }
}
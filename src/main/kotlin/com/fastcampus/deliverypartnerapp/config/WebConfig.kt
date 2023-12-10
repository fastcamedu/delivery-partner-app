package com.fastcampus.deliverypartnerapp.config

import com.fastcampus.deliverypartnerapp.interceptor.LoginInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig: WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginInterceptor())
            .excludePathPatterns(
                "", "/", "/index",
                "/error", "/hello",
                "/favicon.ico",
                "/login/**",
                "/signup/**",
                "/js/**", "/css/**", "/images/**"
            )
    }
}
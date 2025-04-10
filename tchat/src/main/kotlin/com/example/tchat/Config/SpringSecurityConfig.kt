package com.example.tchat.Config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
open class SpringSecurityConfig {
    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val encoder = BCryptPasswordEncoder()

        //Créer des utilisateurs fixes
        auth.inMemoryAuthentication()
            .passwordEncoder(encoder)
            .withUser("aaa")
            .password(encoder.encode("bbb"))
            .roles("USER")
            .and()
            .withUser("Admin")
            .password(encoder.encode("Admin"))
            .roles("ADMIN")
    }

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorize ->
            authorize.requestMatchers("/tchat/saveMessage ").hasRole("ADMIN")
            authorize.requestMatchers("/tchat/saveMessage ").hasRole("USER")
            authorize.requestMatchers("/testPublic").permitAll()
            authorize.requestMatchers("/tchat/allMessages ").hasRole("ADMIN")
            authorize.requestMatchers("/tchat/allMessages ").hasRole("USER")
        }

            .httpBasic { }
            .formLogin { }
            .csrf {
                c -> c.disable()
            }

        return http.build()
    }
}
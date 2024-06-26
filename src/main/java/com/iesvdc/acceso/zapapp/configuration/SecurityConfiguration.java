package com.iesvdc.acceso.zapapp.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, enabled " +
                "from usuario where username = ?")
            .authoritiesByUsernameQuery("select u.username, r.rol as 'authority' " + 
                "from usuario u, rol_usuario r " + 
                "where u.id = r.usuario_id and username = ?");
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        // Con Spring Security 6.2 y 7: usando Lambda DSL

        return http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/webjars/**", "/img/**", "/js/**", "/register", "/register/**", "/ayuda/**", "/acerca/**", "/login", "/denegado")
                .permitAll()
                .requestMatchers("/admin/**", "/admin/*/**", "/admin/*/*/**", "/productos/**", "/productos/*/**", "/productos/*/*/**", "/categorias/**", "/categorias/*/**", "/categorias/*/*/**")
                // .authenticated()
                .hasAuthority("GESTOR")
                .requestMatchers("/operario/**", "/operario/*/**", "/operario/*/*/**", "/operario/*/*/*/**", "/pedidos/**", "/pedidos/*/**", "/pedidos/*/*/**", "/pedidos/*/*/*/**", "/pedidos/*/*/*/*/**")
                // .authenticated()
                .hasAuthority("OPERARIO")
                .requestMatchers("/cliente/**", "/cliente/*/**", "/cliente/*/*/**", "/cliente/*/*/*/**", "/carro/**", "/carro/*/**", "/carro/*/*/**", "/carro/*/*/*/**", "/productos/**", "/productos/*/**", "/productos/*/*/**", "/productos/*/*/*/**")
                // .authenticated()
                .hasAuthority("CLIENTE")
                // .anyRequest().permitAll()
            // ).headers(headers -> headers
                //         .frameOptions(frameOptions -> frameOptions
                //                 .sameOrigin())
                // ).sessionManagement((session) -> session
                //         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)            
            ).exceptionHandling((exception) -> exception
                .accessDeniedPage("/denegado")
            ).formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .permitAll()
            ).rememberMe(
                Customizer.withDefaults()
            ).logout((logout) -> logout
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                // .deleteCookies("JSESSIONID") // No es necesario, JSESSIONID se hace por defecto
                .permitAll()
            ).csrf((protection) -> protection
                .disable()
            // ).cors((protection) -> protection
                // .disable()
            ).build();
    
    }

}

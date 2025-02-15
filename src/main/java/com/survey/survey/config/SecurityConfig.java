package com.survey.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/static/**", "/assets/**","http://192.168.4.206:8080/survey/**").permitAll() // Permitir acceso a archivos estáticos
                        .requestMatchers("/api/users/login", "/api/users/logout", "/login").permitAll()
                        .requestMatchers("/api/users").permitAll()
                        .requestMatchers("/api/data/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/api/users/login")
                        .disable()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/users/logout")
                        .disable()
                )
                .sessionManagement(session -> session
                        .maximumSessions(-1) // Permitir sesiones concurrentes ilimitadas
                        .sessionRegistry(new SessionRegistryImpl())
                );

        return http.build();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://192.168.4.206:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                // Redirige todas las rutas no manejadas a index.html
                registry.addViewController("/{spring:[a-zA-Z0-9-_]+}").setViewName("forward:/index");
                registry.addViewController("/**/{spring:[a-zA-Z0-9-_]+}").setViewName("forward:/index");
                registry.addViewController("/{spring:[a-zA-Z0-9-_]+}/**{spring:[a-zA-Z0-9-_]+}").setViewName("forward:/index");
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = List.of(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja1")
                        .password("caja1")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja2")
                        .password("caja2")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja3")
                        .password("caja3")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja4")
                        .password("caja4")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja5")
                        .password("caja5")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja6")
                        .password("caja6")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("caja7")
                        .password("caja7")
                        .roles("USER")
                        .build()
        );

        return new InMemoryUserDetailsManager(users);
    }
}

package com.java.midtermShopWeb.configurations;

import com.java.midtermShopWeb.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableWebMvc
@ComponentScan
public class WebSecurityConfig2 {
    private final JwtFilter jwtFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix),
                                    String.format("%s/users/refreshToken",apiPrefix))
                            .permitAll()

                            .requestMatchers(GET,
                                    String.format("%s/roles**",apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/categories/**",apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/products/**",apiPrefix)).permitAll()
                            .requestMatchers(POST,
                                    String.format("%s/carts**",apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/carts/**",apiPrefix)).permitAll()
                            .requestMatchers(POST,
                                    String.format("%s/products/**",apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/products/**",apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(GET,
                                    String.format("%s/orders/search**",apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(POST,
                                    String.format("%s/orders/changing-status**",apiPrefix)).hasRole("ADMIN")
                            .requestMatchers(DELETE,
                                    String.format("%s/users/**",apiPrefix)).hasRole(("ADMIN"))
                            .requestMatchers(GET,
                                    String.format("%s/users/search**",apiPrefix)).hasRole(("ADMIN"))
                            .anyRequest()
                            .authenticated();
                }).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}

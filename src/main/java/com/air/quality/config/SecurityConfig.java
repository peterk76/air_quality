package com.air.quality.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        var delegate = new XorCsrfTokenRequestAttributeHandler();
        delegate.setCsrfRequestAttributeName(null);
        CsrfTokenRequestHandler requestHandler = delegate::handle;
        return
                http
                        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/report/worst-cities-no2-y2y").authenticated()
                                .requestMatchers("/city/**").authenticated()
                                .requestMatchers("/city/note/add").authenticated()
                                .requestMatchers("/city/note/edit").authenticated()
                                .anyRequest().permitAll())
                        .formLogin(login -> login
                                .successHandler((request, response, authentication) ->
                                        response.setStatus(HttpStatus.OK.value()))
                                .failureHandler((request, response, exception) ->
                                        response.setStatus(HttpStatus.UNAUTHORIZED.value())))
                        .cors(withDefaults())
                        .exceptionHandling(exception ->
                                exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                        .csrf(csrf -> csrf
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                .csrfTokenRequestHandler(requestHandler)
                                .ignoringRequestMatchers("/login")
                                .ignoringRequestMatchers("/api/save-measure")
                                .ignoringRequestMatchers("/city/note/add")
                                .ignoringRequestMatchers("/city/note/edit"))
                        .sessionManagement(httpManag -> httpManag.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                        .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();
        config.addAllowedHeader("X-XSRF-TOKEN");
        config.addAllowedHeader("Content-Type");
        config.addExposedHeader("Content-Disposition");
        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedOrigins(List.of("http://localhost:5173/"));
        config.setAllowCredentials(true);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    @SuppressWarnings({"java:S6437"}) // just example users
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var admin = User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("USER")
                .build();
        var admin1 = User.withUsername("admin1")
                .password(encoder.encode("admin1"))
                .roles("USER")
                .build();
        var admin2 = User.withUsername("admin2")
                .password(encoder.encode("admin2"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, admin1, admin2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

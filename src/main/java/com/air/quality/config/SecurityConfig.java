package com.air.quality.config;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
//@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class SecurityConfig {

    //final LdapConfig ldapConfig; // TODO

    /*@Value("${frontend.url}")
    String frontendUrl;*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //var tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        var delegate = new XorCsrfTokenRequestAttributeHandler();
        delegate.setCsrfRequestAttributeName(null);
        CsrfTokenRequestHandler requestHandler = delegate::handle;
        return
                http
                        .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests
                                        .requestMatchers("/api/stats/**").permitAll()
                                        //.requestMatchers("/api/report/**").permitAll() // TODO
                                        .requestMatchers("/city/**").permitAll() // TODO
                                        .requestMatchers("/api/save-measure").permitAll()
                                        //.anyRequest().permitAll()
                                        .anyRequest().authenticated()
                        )
                        .formLogin(login ->
                                login.successHandler((request, response, authentication) ->
                                        response.setStatus(HttpStatus.OK.value())))
                        .cors(Customizer.withDefaults())
                        .exceptionHandling(exception ->
                                exception.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                        .csrf(csrf ->
                                        csrf
                                                .disable()
                                // ---
                                        /*.csrfTokenRepository(tokenRepository)
                                        .csrfTokenRequestHandler(requestHandler)*/
                        )
                        .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("X-XSRF-TOKEN");
        config.addAllowedHeader("Content-Type");
        config.addExposedHeader("Content-Disposition");
        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedOrigins(List.of("http://localhost:5173/"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /*@SuppressWarnings({"squid:S5344", "squid:S1874", "squid:S4790"}) // TODO
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .contextSource()
                    .url(ldapConfig.getLdapUrl())
                    .managerDn(ldapConfig.getManagerDn())
                    .managerPassword(ldapConfig.getManagerPassword())
                    .and()
                .userDnPatterns(ldapConfig.getFullUserDnPattern())
                .groupSearchBase(ldapConfig.getFullGroupSearchBase())
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute(ldapConfig.getPasswordAttribute());
    }*/
}

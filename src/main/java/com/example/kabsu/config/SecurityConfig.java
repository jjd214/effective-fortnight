package com.example.kabsu.config;

import com.example.kabsu.security.JwtFilter;
import com.example.kabsu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // don't go for default spring security, instead do this shit
public class SecurityConfig {

    private static final String[] PUBLIC_URLS = {
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/api/v1/auth/refresh"
    };

    // Inject UserService to use UserDetailsService
    private final UserService userService;
    // Inject passwordEncoder bean from AppConfig
    private final PasswordEncoder passwordEncoder;
    // Inject JwtFilter to filter first or authenticate the JWT aut before UsernameAndPassword
    private final JwtFilter jwtFilter;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        // reads hashPassword when logging in.
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // use authentication (use explicitly) / it is use implicitly in AppConfig but better to explicitly too.
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS)
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //    Disabled CSRF T
//        http.csrf(AbstractHttpConfigurer::disable);
//        Any request should be authorized / Need authenticated user if no it's gonna return access denied page
//        http.authorizeHttpRequests(request ->
//                request.anyRequest().authenticated());
//        Enable login form / Response is login form
//        http.formLogin(Customizer.withDefaults());
//        Enable login form / Postman
//        http.httpBasic(Customizer.withDefaults());
//        Generate authentication info or id each request / While stateful saves Authentication info in session id which stateless does not.
//        http.sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


//    @Bean
//    public UserDetailsService userDetailsService() {
//        // Hardcode shit
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("johndoe")
//                .password("test")
//                .roles("USER")
//                .build();
//        // This sets to not use the default service / return our own UserDetailsService object.
//        return new InMemoryUserDetailsManager(user1);
//    }

}

package it.epicode.W7_D5_BE_project.security;

import it.epicode.W7_D5_BE_project.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtFilter jwtFilter) throws Exception {
        // primo metodo serve per creare in automatico una pagina di login, disabled
        httpSecurity.formLogin(http->http.disable());
        //csrf serve per evitare la possibilità di utilizzi di sessioni lasciate aperte
        httpSecurity.csrf(http->http.disable());
        // non ci interessa perchè i servizi rest non hanno sessione
        httpSecurity.sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // non ho capito. serve per bloccare richiesta da indirizzi ip e porte diversi da dove si trova il servizio
        httpSecurity.cors(Customizer.withDefaults());

        // prevede la approvazione o negazione di un servizio endpoint
        httpSecurity.authorizeHttpRequests(http->http.requestMatchers("/auth/**").permitAll());

        httpSecurity.authorizeHttpRequests(auth -> auth

                // organizzatori
                .requestMatchers(HttpMethod.POST, "/eventi/**").hasRole("ORGANIZZATORE")
                .requestMatchers(HttpMethod.PUT, "/eventi/**").hasRole("ORGANIZZATORE")
                .requestMatchers(HttpMethod.PATCH, "/eventi/**").hasRole("ORGANIZZATORE")
                .requestMatchers(HttpMethod.DELETE, "/eventi/**").hasRole("ORGANIZZATORE")

                // utenti
                .requestMatchers(HttpMethod.POST, "/prenotazioni/**").hasRole("UTENTE")
                .requestMatchers(HttpMethod.DELETE, "/prenotazioni/**").hasRole("UTENTE")


                // Tutto il resto deve essere autenticato
                .anyRequest().authenticated()
        );


       // httpSecurity.authorizeHttpRequests(http->http.anyRequest().denyAll());


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    // cors
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}

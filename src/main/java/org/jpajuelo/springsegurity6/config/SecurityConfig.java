package org.jpajuelo.springsegurity6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    /*
    //CREAMOS LAS CONDICIONES PERSONALIZADAS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())   //Vulnerabilidad web    //Proteccion de esa vulnerabilidad // REST no es necesario
                .httpBasic(Customizer.withDefaults())                //Se usa cuando se logear con user y password    //Cuando se usa tokens se maneja diferente
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))           //Depende del tiempo de vida del token
                .authorizeHttpRequests(http->{
                    //Configurar endpoints publicos
                    http.requestMatchers(HttpMethod.GET, "/auth/one").permitAll();
                    //Configurar endpoints privados
                    http.requestMatchers(HttpMethod.GET, "/auth/two").hasAuthority("CREATE");
                    //Config Resto endpoints
                    http.anyRequest().denyAll();
                }).build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Elejimos el proveedor
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //proveedor DAO
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    UserDetailsService userDetailsService() {

        List<UserDetails> userDetailsService = new ArrayList<>();
        userDetailsService.add(User.withUsername("Jhonnatan")
                .password("12345")
                .roles("ADMIN")
                .authorities("READ","CREATE")
                .build());

        userDetailsService.add(User.withUsername("Valeria")
                .password("12345")
                .roles("ADMIN")
                .authorities("CREATE")
                .build());

        userDetailsService.add(User.withUsername("Josselyn")
                .password("123")
                .roles("USER")
                .authorities("READ")
                .build());

        return new InMemoryUserDetailsManager(userDetailsService);
    }

}

package br.edu.utfpr.pb.tads.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Indica que é uma classe de configuração.
@EnableWebSecurity // Faz com que tudo nesta classe habilite a configuração do WebSecurity.
public class SecurityConfiguration {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfiguration(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Autowired
    SecurityFilter securityFilter;

    /*configura uma aplicação Spring Security para desabilitar proteção CSRF,
    tratar sessões como stateless, e requerer que apenas usuários com a função "ADMIN"
    possam realizar requisições POST para "/products", enquanto todas as outras requisições
    exigem autenticação.*/

    @Bean
    //Faz validações/filtragens para tornar a aplicação segura.
    //Verifica se o User esta apto a fazer uma requisição.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint)) // Adiciona o AuthenticationEntryPoint personalizado
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() //Permite que todos os usuários façam um post nesta url de login.
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() //Permite que todos os usuários façam um post nesta url de registro.
                                .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();//.build serve para construir um objeto.

        // STATELESS --> Estado utilizado para remover tokens do tipo csrf e permitir definir tokens da biblioteca JWT para os usuários.

    }

    @Bean  //Faz a injeção de dependência, para poder ser usado por toda a plicação.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Método para criptografar a senha.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Classe do SpringSecurity para fazer criptografia das senhas.
    }
}

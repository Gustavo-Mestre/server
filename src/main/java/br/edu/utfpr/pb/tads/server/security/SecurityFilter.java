package br.edu.utfpr.pb.tads.server.security;

import br.edu.utfpr.pb.tads.server.repository.UserRepository;
import br.edu.utfpr.pb.tads.server.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private final TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    /*implementa o que o filtro deve fazer com cada solicitação e resposta HTTP
    antes de passá-las adiante no cadeia de filtros (ou terminar o processamento
    da solicitação).
    É nesse método que é possível interceptar, examinar, modificar ou
    adicionar verificações de segurança adicionais às requisições HTTP.*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var name = tokenService.validateToken(token); //Retorna o nome do usuário associado ao token.
            UserDetails user = userRepository.findByName(name); //Busca o usuário com este nome no banco.

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); //Cria um objeto de autenticação contendo o usuário e suas autoridades (roles/permissões
            SecurityContextHolder.getContext().setAuthentication(authentication); // Pemritew que a pljcaco saiba qual é o user autenticado e verifique as suas roles.
        }
        filterChain.doFilter(request, response); //chama o proximo filtro, caso não tnha token.
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization"); //Esta linha recupera o conteúdo do cabeçalho Authorization da solicitação HTTP
        if (authHeader == null) return null; //Se for nulo e porque não tem token
        return authHeader.replace("Bearer ", ""); //substitui o valor bearer que vem padrao nas requisicoes por um espaco vazio, de modo a identifcar soimente o token e o retorna para a funcao.
    }
}

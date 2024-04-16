package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.dto.AuthenticationResponseDto;
import br.edu.utfpr.pb.tads.server.dto.LoginRecordDto;
import br.edu.utfpr.pb.tads.server.dto.RegisterDto;
import br.edu.utfpr.pb.tads.server.model.UserModel;
import br.edu.utfpr.pb.tads.server.repository.UserRepository;
import br.edu.utfpr.pb.tads.server.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth") //Sempre que houver um endpoint auth, cairá neste controller.
public class AuthController {

    //Injeta o AuthenticationManager, que vem do Spring Security.
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository; //Injetado para verificar se o usuário já foi cadastrado no banco.
    @Autowired
    private TokenService tokenService;
    //Requisição para permitir o login do user.
    // endpoint "/auth/login
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid LoginRecordDto loginRecordDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRecordDto.name(), loginRecordDto.password()); //Cria um objeto para armezenar as credenciais do user.
        var auth = this.authenticationManager.authenticate(usernamePassword); //Autentica o usuário com as credenciais informadas acima.

        var token = tokenService.generateToken((UserModel)auth.getPrincipal()); //Criação do token.
        return ResponseEntity.ok(new AuthenticationResponseDto(token)); //Retorna o token para a requisição.
    }

    //Post para registrar usuário.
    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDto registerDto){
        if(userRepository.findByName(registerDto.name()) != null)return ResponseEntity.badRequest().build(); // Se for diferente de nulo é porque já existe, então ele não cria.

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        UserModel newUser = new UserModel(registerDto.name(), encryptedPassword, registerDto.role());

        this.userRepository.save(newUser); //Adiciona o User no banco de dados.

        return ResponseEntity.ok().build(); //Retorna a entidade.
    }
}

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    //Post para login.
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRecordDto loginRecordDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRecordDto.name(), loginRecordDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new AuthenticationResponseDto(token));
    }

    //Post para registrar usuário.
    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterDto registerDto) {
        if (userRepository.findByName(registerDto.name()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        UserModel newUser = new UserModel(registerDto.name(), encryptedPassword, registerDto.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }

}

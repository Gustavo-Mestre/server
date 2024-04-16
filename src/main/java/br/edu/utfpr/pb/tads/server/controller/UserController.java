package br.edu.utfpr.pb.tads.server.controller;

import br.edu.utfpr.pb.tads.server.model.UserModel;
import br.edu.utfpr.pb.tads.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController //Tem que ser usado para fazer a injeção dos beans.
public class UserController {

    @Autowired
    UserRepository userRepository;

    //Traz todos os usuários registrados no banco.
    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    //Traz o usuário de acordo com o id.
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable (value = "id") Long id){
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isEmpty()) return ResponseEntity.status((HttpStatus.NOT_FOUND)).body("User not found.");
        return ResponseEntity.status(HttpStatus.OK).body(user.get());
    }

}

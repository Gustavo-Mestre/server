package br.edu.utfpr.pb.tads.server.service;

import br.edu.utfpr.pb.tads.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service //Anotação que inica ao Spring que este é um serviço da aplicação.
public class AuthService implements UserDetailsService {//Faz com que o Spring chame esta classe automaticamente.

    @Autowired
    UserRepository userRepository;

    //Este método fará a consulta dos usuários cadastrados para o SpringSecurity.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }
}

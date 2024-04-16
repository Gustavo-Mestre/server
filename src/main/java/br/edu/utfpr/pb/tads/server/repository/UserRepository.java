package br.edu.utfpr.pb.tads.server.repository;


import br.edu.utfpr.pb.tads.server.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserDetails findByName(String name); //Metodo para fazer um get pelo nome do usuário, retotrnando um UserDetais.
}

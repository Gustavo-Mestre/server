package br.edu.utfpr.pb.tads.server.repository;


import br.edu.utfpr.pb.tads.server.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserDetails findByName(String name);

    Optional<UserModel> findUserByName(String name);

}

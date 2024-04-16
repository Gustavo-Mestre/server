package br.edu.utfpr.pb.tads.server.model;


import br.edu.utfpr.pb.tads.server.model.user.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "TB_USERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of = "id")

public class UserModel implements UserDetails{ //Classe usada para autenticação de usuários.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "{br.edu.utfpr.pb.tads.server.UserModel.name.constraints.NotNull.message}")
    @Size(min = 4, max = 255, message = "{br.edu.utfpr.pb.tads.server.UserModel.name.constraints.Size.message}")
    private String name;

    @NotNull(message = "{br.edu.utfpr.pb.tads.server.UserModel.password.constraints.NotNull.message}")
    @Size(min = 6, message = "{br.edu.utfpr.pb.tads.server.UserModel.password.constraints.Size.message}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{br.edu.utfpr.pb.tads.server.UserModel.password.constraints.Pattern.message}")
    private String password;

    private UserRole role;

    public UserModel (String name, String password, UserRole role){
        this.name = name;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

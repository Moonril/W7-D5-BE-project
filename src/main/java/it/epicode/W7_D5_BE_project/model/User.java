package it.epicode.W7_D5_BE_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.W7_D5_BE_project.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.UTENTE;


    @JsonIgnore
    @OneToMany(mappedBy = "utente")
    private List<Prenotazione> prenotazione;
    @JsonIgnore
    @OneToMany(mappedBy = "utente")
    private List<Evento> eventi;


    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;

    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;

    }

    @Override
    public boolean isEnabled() {
        //return UserDetails.super.isEnabled();
        return true;

    }
}

package it.epicode.W7_D5_BE_project.service;


import it.epicode.W7_D5_BE_project.dto.LoginDto;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.model.User;
import it.epicode.W7_D5_BE_project.repository.UserRepository;
import it.epicode.W7_D5_BE_project.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginDto loginDto) throws NotFoundException {
        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> new NotFoundException("Utente con username: " + loginDto.getUsername() + "non trovato"));

        if(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            return jwtTool.createToken(user);
        } else {
            throw new NotFoundException("Utente con username/password: " + loginDto.getUsername() + "non trovato");
        }

    }
}

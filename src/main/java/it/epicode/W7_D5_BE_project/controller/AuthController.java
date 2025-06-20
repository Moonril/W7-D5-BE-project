package it.epicode.W7_D5_BE_project.controller;


import it.epicode.W7_D5_BE_project.dto.LoginDto;
import it.epicode.W7_D5_BE_project.dto.UserDto;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.model.User;
import it.epicode.W7_D5_BE_project.service.AuthService;
import it.epicode.W7_D5_BE_project.service.UserService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private it.epicode.W7_D5_BE_project.service.UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("", (s,e)->s+e));
        }

        return userService.saveUser(userDto);
    }

    @GetMapping("/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("", (s,e)->s+e));
        }

        return authService.login(loginDto);
    }
}

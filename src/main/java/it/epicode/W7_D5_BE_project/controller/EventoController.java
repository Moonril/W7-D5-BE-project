package it.epicode.W7_D5_BE_project.controller;

import it.epicode.W7_D5_BE_project.dto.EventoDto;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.model.Evento;
import it.epicode.W7_D5_BE_project.service.EventoService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping(path = "/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Evento saveEvento (@RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult) throws NotFoundException, ValidationException, AccessDeniedException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));
        }
        return eventoService.saveEvento(eventoDto);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Page<Evento> getAllEventi(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue =  "id") String sortBy){
        return eventoService.getAllEventi(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable int id) throws NotFoundException {
        return eventoService.getEvento(id);
    }

    @PutMapping("/{id}")
    public Evento updateViaggio(@PathVariable int id, @RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult) throws NotFoundException, ValidationException, AccessDeniedException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));
        }
        return eventoService.updateEvento(id, eventoDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable int id) throws NotFoundException{
        eventoService.deleteEvento(id);
    }

//    @PatchMapping("/{id}")
//    public Evento patchStatoEvento(@PathVariable int id, @RequestParam String stato) throws NotFoundException {
//        return eventoService.patchEvento(id, stato);
//    }
}
package it.epicode.W7_D5_BE_project.controller;

import it.epicode.W7_D5_BE_project.dto.PrenotazioneDto;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.model.Prenotazione;
import it.epicode.W7_D5_BE_project.service.PrenotazioneService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione (@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Page<Prenotazione> getAllPrenotazioni(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue =  "id") String sortBy){
        return prenotazioneService.getAllPrenotazioni(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable int id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione(@PathVariable int id, @RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(e, s)->e+s));
        }
        return prenotazioneService.updatePrenotazione(id, prenotazioneDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(@PathVariable int id) throws NotFoundException{
        prenotazioneService.deletePrenotazione(id);
    }
}

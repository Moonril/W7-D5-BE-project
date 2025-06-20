package it.epicode.W7_D5_BE_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDto {
    @NotEmpty(message = "inserisci un titolo")
    private String titolo;
    @NotEmpty(message = "inserisci una descrizione")
    private String descrizione;
    @NotNull(message = "Inserisci una data corretta")
    private LocalDate dataEvento;
    @NotEmpty(message = "inserisci un luogo per l'evento")
    private String luogo;
    @NotNull(message = "Inserisci il numero di posti disponibili")
    private int numeroPosti;

    private int userId;

}

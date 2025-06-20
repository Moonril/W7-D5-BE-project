package it.epicode.W7_D5_BE_project.dto;

import it.epicode.W7_D5_BE_project.model.Evento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrenotazioneDto {

    @NotNull(message = "prenota almeno un posto")
    private int postiPrenotati;

    private int eventoId;
    private int userId;

}

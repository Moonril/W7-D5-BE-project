package it.epicode.W7_D5_BE_project.model;

import it.epicode.W7_D5_BE_project.enums.StatoPrenotazione;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue
    private int id;
    private int postiPrenotati;
    private LocalDate dataPrenotazione = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private StatoPrenotazione stato = StatoPrenotazione.ATTIVA;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

}

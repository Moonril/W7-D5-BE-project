package it.epicode.W7_D5_BE_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Evento {
    @Id
    @GeneratedValue
    private int id;
    private String titolo;
    private String descrizione;
    private LocalDate dataEvento;
    private String luogo;
    private int numeroPosti;


    @ManyToOne
    @JoinColumn(name = "utente_id")
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy = "evento")
    private List<Prenotazione> prenotazioni;
}

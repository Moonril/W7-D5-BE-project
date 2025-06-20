package it.epicode.W7_D5_BE_project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Prenotazione {

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private User user;

}

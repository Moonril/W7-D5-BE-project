package it.epicode.W7_D5_BE_project.repository;

import it.epicode.W7_D5_BE_project.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
}

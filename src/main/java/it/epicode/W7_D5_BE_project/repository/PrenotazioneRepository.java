package it.epicode.W7_D5_BE_project.repository;

import it.epicode.W7_D5_BE_project.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
    @Query("SELECT COALESCE(SUM(p.postiPrenotati), 0) FROM Prenotazione p WHERE p.evento.id = :eventoId")
    int countPostiPrenotatiByEvento(@Param("eventoId") int eventoId);
}

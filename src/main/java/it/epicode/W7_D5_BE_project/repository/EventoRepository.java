package it.epicode.W7_D5_BE_project.repository;

import it.epicode.W7_D5_BE_project.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
}

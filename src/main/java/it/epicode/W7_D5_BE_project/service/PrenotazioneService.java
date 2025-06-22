package it.epicode.W7_D5_BE_project.service;

import it.epicode.W7_D5_BE_project.dto.EventoDto;
import it.epicode.W7_D5_BE_project.dto.PrenotazioneDto;
import it.epicode.W7_D5_BE_project.enums.Role;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.exceptions.PostiEsauritiException;
import it.epicode.W7_D5_BE_project.model.Evento;
import it.epicode.W7_D5_BE_project.model.Prenotazione;
import it.epicode.W7_D5_BE_project.model.User;
import it.epicode.W7_D5_BE_project.repository.EventoRepository;
import it.epicode.W7_D5_BE_project.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UserService userService;

    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException, AccessDeniedException {
        Evento evento = eventoService.getEvento(prenotazioneDto.getEventoId());

        User utenteAutenticato = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        if (utenteAutenticato.getRole() != Role.UTENTE) {
            throw new AccessDeniedException("Solo gli utenti possono effettuare prenotazioni");
        }

        int postiRichiesti = prenotazioneDto.getPostiPrenotati();
        int postiPrenotati = prenotazioneRepository.countPostiPrenotatiByEvento(evento.getId());
        int postiDisponibili = evento.getNumeroPosti() - postiPrenotati;

        if (postiDisponibili < postiRichiesti) {
            throw new PostiEsauritiException("Posti esauriti o insufficienti per questo evento");
        }

        evento.setNumeroPosti(postiDisponibili - postiRichiesti);
        eventoRepository.save(evento);
        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setPostiPrenotati(prenotazioneDto.getPostiPrenotati());
        prenotazione.setEvento(evento);

        prenotazione.setUser(utenteAutenticato);


        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con id: " + id + " non trovata"));
    }

    public Page<Prenotazione> getAllPrenotazioni(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione updatePrenotazione(int id, PrenotazioneDto prenotazioneDto) throws NotFoundException, AccessDeniedException {
        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);

        User utenteAutenticato = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (prenotazioneDaAggiornare.getUser().getId() != utenteAutenticato.getId()) {
            throw new AccessDeniedException("Non sei autorizzato a modificare questa prenotazione.");
        }


        if (utenteAutenticato.getRole() != Role.UTENTE) {
            throw new AccessDeniedException("Solo gli utenti possono modificare prenotazioni.");
        }


        prenotazioneDaAggiornare.setPostiPrenotati(prenotazioneDto.getPostiPrenotati());


        if (prenotazioneDaAggiornare.getEvento().getId() != prenotazioneDto.getEventoId()) {
            Evento nuovoEvento = eventoService.getEvento(prenotazioneDto.getEventoId());
            prenotazioneDaAggiornare.setEvento(nuovoEvento);
        }

        return prenotazioneRepository.save(prenotazioneDaAggiornare);
    }

    public void deletePrenotazione(int id) throws NotFoundException{
        Prenotazione prenotazioneToDelete = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneToDelete);
    }

}

package it.epicode.W7_D5_BE_project.service;

import it.epicode.W7_D5_BE_project.dto.EventoDto;
import it.epicode.W7_D5_BE_project.dto.PrenotazioneDto;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.model.Evento;
import it.epicode.W7_D5_BE_project.model.Prenotazione;
import it.epicode.W7_D5_BE_project.model.User;
import it.epicode.W7_D5_BE_project.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UserService userService;

    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException {
        Evento evento = eventoService.getEvento(prenotazioneDto.getEventoId());
        User user = userService.getUser(prenotazioneDto.getUserId());

//        boolean giaPrenotato = prenotazioneRepository
//                .existsByDipendenteIdAndViaggio_DataViaggio(dipendente.getId(), viaggio.getDataViaggio());
//
//        if (giaPrenotato) {
//            throw new PrenotazioneGiaEsistenteException("Il dipendente ha già una prenotazione per quel giorno.");
//        }

        Prenotazione prenotazione = new Prenotazione();

        prenotazione.setPostiPrenotati(prenotazioneDto.getPostiPrenotati());
        prenotazione.setEvento(evento);
        prenotazione.setUser(user);

        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione getPrenotazione(int id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con id: " + id + " non trovata"));
    }

    public Page<Prenotazione> getAllPrenotazioni(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione updatePrenotazione(int id, PrenotazioneDto prenotazioneDto) throws NotFoundException{
        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);

        prenotazioneDaAggiornare.setPostiPrenotati(prenotazioneDto.getPostiPrenotati());


        if(prenotazioneDaAggiornare.getEvento().getId()!=prenotazioneDto.getEventoId()){
            Evento evento = eventoService.getEvento(prenotazioneDto.getEventoId());
            prenotazioneDaAggiornare.setEvento(evento);
        }
        return prenotazioneRepository.save(prenotazioneDaAggiornare);
    }

    public void deletePrenotazione(int id) throws NotFoundException{
        Prenotazione prenotazioneToDelete = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneToDelete);
    }

}

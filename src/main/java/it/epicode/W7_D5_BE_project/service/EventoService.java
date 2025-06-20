package it.epicode.W7_D5_BE_project.service;

import it.epicode.W7_D5_BE_project.dto.EventoDto;
import it.epicode.W7_D5_BE_project.enums.Role;
import it.epicode.W7_D5_BE_project.exceptions.NotFoundException;
import it.epicode.W7_D5_BE_project.model.Evento;
import it.epicode.W7_D5_BE_project.model.User;
import it.epicode.W7_D5_BE_project.repository.EventoRepository;
import it.epicode.W7_D5_BE_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserService userService;



    public Evento saveEvento(EventoDto eventoDto) throws NotFoundException, AccessDeniedException {

        User utenteAutenticato = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();


        if (utenteAutenticato.getRole() != Role.ORGANIZZATORE) {
            throw new AccessDeniedException("Solo gli organizzatori possono creare eventi.");
        }

        Evento evento = new Evento();
        evento.setUser(utenteAutenticato);

        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setDataEvento(eventoDto.getDataEvento());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setNumeroPosti(eventoDto.getNumeroPosti());

        return eventoRepository.save(evento);
    }

    public Evento getEvento(int id) throws NotFoundException {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException("Viaggio con id: " + id + " non trovato"));
    }

    public Page<Evento> getAllEventi(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventoRepository.findAll(pageable);
    }

    public Evento updateEvento(int id, EventoDto eventoDto) throws NotFoundException, AccessDeniedException {
        Evento eventoDaAggiornare = getEvento(id);

        User utenteAutenticato = (User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();


        if (eventoDaAggiornare.getUser().getId() != utenteAutenticato.getId()) {
            throw new AccessDeniedException("Non sei autorizzato a modificare questo evento.");
        }


        if (utenteAutenticato.getRole() != Role.ORGANIZZATORE) {
            throw new AccessDeniedException("Solo gli organizzatori possono modificare eventi.");
        }

        eventoDaAggiornare.setTitolo(eventoDto.getTitolo());
        eventoDaAggiornare.setDescrizione(eventoDto.getDescrizione());
        eventoDaAggiornare.setDataEvento(eventoDto.getDataEvento());
        eventoDaAggiornare.setLuogo(eventoDto.getLuogo());
        eventoDaAggiornare.setNumeroPosti(eventoDto.getNumeroPosti());

        return eventoRepository.save(eventoDaAggiornare);
    }

    public void deleteEvento(int id) throws NotFoundException{
        Evento eventoDaCancellare = getEvento(id);
        eventoRepository.delete(eventoDaCancellare);
    }


}

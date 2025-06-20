package it.epicode.W7_D5_BE_project.service;

import it.epicode.W7_D5_BE_project.dto.EventoDto;
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
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UserService userService;



    public Evento saveEvento(EventoDto eventoDto) throws NotFoundException {
        User organizzatore = userService.getUser(eventoDto.getUserId());

        Evento evento = new Evento();

        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setDataEvento(eventoDto.getDataEvento());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setNumeroPosti(eventoDto.getNumeroPosti());
        evento.setUser(organizzatore);

        return eventoRepository.save(evento);
    }

    public Evento getEvento(int id) throws NotFoundException {
        return eventoRepository.findById(id).orElseThrow(() -> new NotFoundException("Viaggio con id: " + id + " non trovato"));
    }

    public Page<Evento> getAllEventi(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return eventoRepository.findAll(pageable);
    }

    public Evento updateEvento(int id, EventoDto eventoDto) throws NotFoundException{
        Evento eventoDaAggiornare = getEvento(id);

        eventoDaAggiornare.setTitolo(eventoDto.getTitolo());
        eventoDaAggiornare.setDescrizione(eventoDto.getDescrizione());
        eventoDaAggiornare.setDataEvento(eventoDto.getDataEvento());
        eventoDaAggiornare.setLuogo(eventoDto.getLuogo());
        eventoDaAggiornare.setNumeroPosti(eventoDto.getNumeroPosti());

        // todo if?

        return eventoRepository.save(eventoDaAggiornare);
    }

    public void deleteEvento(int id) throws NotFoundException{
        Evento eventoDaCancellare = getEvento(id);
        eventoRepository.delete(eventoDaCancellare);
    }


}

package com.example.gestione_prenotazioni.service;


import com.example.gestione_prenotazioni.model.Utente;
import com.example.gestione_prenotazioni.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
    private final UtenteRepository  utenteRepository;

    public UtenteService (UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }
    public Utente creaUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
    public Optional<Utente> trovaPerId(Long id) {
        return utenteRepository.findById(id);
    }
    public List<Utente> trovaTutti() {
        return utenteRepository.findAll();
    }
}

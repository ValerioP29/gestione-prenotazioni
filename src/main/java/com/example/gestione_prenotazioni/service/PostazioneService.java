package com.example.gestione_prenotazioni.service;

import com.example.gestione_prenotazioni.model.Postazione;
import com.example.gestione_prenotazioni.model.TipoPostazione;
import com.example.gestione_prenotazioni.model.Utente;
import com.example.gestione_prenotazioni.repository.PostazioneRepository;
import com.example.gestione_prenotazioni.repository.PrenotazioneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostazioneService {
    private final PostazioneRepository postazioneRepository;
    private final PrenotazioneService prenotazioneService;


    public PostazioneService(PostazioneRepository postazioneRepository, PrenotazioneService prenotazioneService) {
        this.postazioneRepository = postazioneRepository;
        this.prenotazioneService = prenotazioneService;

    }

    public Postazione creaPostazione(Postazione postazione) {
        return postazioneRepository.save(postazione);
    }

    public List<Postazione> cercaPerTipoEcitta(TipoPostazione tipo, String citta) {
        return postazioneRepository.findByTipoAndEdificio_Citta(tipo, citta);
    }

    public List<Postazione> trovaTutte() {
        return postazioneRepository.findAll();
    }


    public boolean postazioneDisponibile(Postazione postazione, LocalDate data) {

        return prenotazioneService.postazioneDisponibile(postazione,data);
    }


    public String prenotaPostazione(Postazione postazione, Utente utente, LocalDate data) {
        if (!postazioneDisponibile(postazione, data)) {
            return "Errore: La postazione è già prenotata per questa data.";
        }

        return prenotazioneService.prenotaPostazione(utente, postazione, data);
    }
}

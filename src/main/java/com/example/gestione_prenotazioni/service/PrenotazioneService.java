package com.example.gestione_prenotazioni.service;

import com.example.gestione_prenotazioni.model.Postazione;
import com.example.gestione_prenotazioni.model.Prenotazione;
import com.example.gestione_prenotazioni.model.TipoPostazione;
import com.example.gestione_prenotazioni.model.Utente;
import com.example.gestione_prenotazioni.repository.PrenotazioneRepository;
import com.example.gestione_prenotazioni.repository.PostazioneRepository;
import com.example.gestione_prenotazioni.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private PostazioneRepository postazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public boolean postazioneDisponibile(Postazione postazione, LocalDate data) {
        return prenotazioneRepository.countByPostazioneAndData(postazione, data) == 0;
    }

    public String prenotaPostazione(Utente utente, Postazione postazione, LocalDate data) {

        if (prenotazioneRepository.countByPostazioneAndData(postazione, data) > 0) {
            return "Errore: La postazione è già prenotata per questa data.";
        }


        if (prenotazioneRepository.countByUtenteAndData(utente, data) > 0) {
            return "Errore: L'utente ha già una prenotazione per questa data.";
        }


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(utente);
        prenotazione.setPostazione(postazione);
        prenotazione.setData(data);
        prenotazioneRepository.save(prenotazione);

        return "Prenotazione effettuata con successo!";
    }


    public String ricercaPostazioni(TipoPostazione tipoPostazione, String citta) {
        var postazioni = postazioneRepository.findByTipoAndEdificio_Citta(tipoPostazione, citta);
        if (postazioni.isEmpty()) {
            return "Nessuna postazione trovata per i criteri selezionati.";
        }

        StringBuilder risultato = new StringBuilder("Postazioni disponibili:\n");
        for (Postazione p : postazioni) {
            risultato.append(p.getCodice()).append(" - ").append(p.getDescrizione()).append("\n");
        }
        return risultato.toString();
    }
}

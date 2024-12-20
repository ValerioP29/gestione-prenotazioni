package com.example.gestione_prenotazioni.repository;

import com.example.gestione_prenotazioni.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
}

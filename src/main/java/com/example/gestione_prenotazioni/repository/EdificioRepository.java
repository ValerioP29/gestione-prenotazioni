package com.example.gestione_prenotazioni.repository;

import com.example.gestione_prenotazioni.model.Edificio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EdificioRepository extends JpaRepository<Edificio, Long> {
}

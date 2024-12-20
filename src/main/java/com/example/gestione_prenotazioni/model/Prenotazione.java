package com.example.gestione_prenotazioni.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity

public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Utente utente;

    @ManyToOne(optional = false)
    private Postazione postazione;

    @Column(nullable = false)
    private LocalDate data;
}

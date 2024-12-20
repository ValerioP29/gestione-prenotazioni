package com.example.gestione_prenotazioni.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Postazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codice;

    @Column(nullable = false, length = 100)
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPostazione tipo;

    @Column(nullable = false)
    private Integer maxOccupanti;

    @ManyToOne(optional = false)
    private Edificio edificio;
}


package com.example.gestione_prenotazioni.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column (nullable = false, length = 50)
    private String nomeCompleto;
    @Column(nullable = false, unique = true, length = 100)
    private String email;


}

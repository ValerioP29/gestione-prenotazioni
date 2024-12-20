package com.example.gestione_prenotazioni.model;




import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column (nullable = false, unique = true, length = 50)
    private String nome;

    @Column(nullable = false, length = 100)
    private String indirizzo;

    @Column (nullable = false, length = 50)
    private String citta;


}

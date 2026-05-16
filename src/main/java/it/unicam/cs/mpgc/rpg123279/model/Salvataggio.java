package it.unicam.cs.mpgc.rpg123279.model;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salvataggi")
public class Salvataggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Giocatore giocatore;

    @Column(nullable = false)
    private LocalDateTime dataSalvataggio;

    @Column(nullable = false)
    private String nomeSlot;

    public Salvataggio() {}
    public Salvataggio(Giocatore giocatore, String nomeSlot) {
        this.giocatore = giocatore;
        this.nomeSlot = nomeSlot;
        this.dataSalvataggio = LocalDateTime.now();
    }
}

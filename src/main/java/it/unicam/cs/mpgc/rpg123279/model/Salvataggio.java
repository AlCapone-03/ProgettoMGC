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

    public Long getId() { return id; }
    public Giocatore getGiocatore() { return giocatore; }
    public void setGiocatore(Giocatore giocatore) { this.giocatore = giocatore; }
    public LocalDateTime getDataSalvataggio() { return dataSalvataggio; }
    public void setDataSalvataggio(LocalDateTime dataSalvataggio) {this.dataSalvataggio = dataSalvataggio;}
    public String getNomeSlot() { return nomeSlot; }
    public void setNomeSlot(String nomeSlot) { this.nomeSlot = nomeSlot; }
}
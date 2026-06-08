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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "giocatore_id", nullable = false)
    private Giocatore giocatore;

    @Column(nullable = false)
    private LocalDateTime dataSalvataggio;

    @Column(nullable = false)
    private String nomeSlot;

    @Column(nullable = false)
    private String nomeIsolaCorrente;

    @Column(nullable = false, length = 500)
    private String isoleBossSconfitto;

    public Salvataggio() {}
    public Salvataggio(Giocatore giocatore, String nomeSlot, String nomeIsolaCorrente, String isoleBossSconfitto) {
        this.giocatore= giocatore;
        this.nomeSlot= nomeSlot;
        this.nomeIsolaCorrente= nomeIsolaCorrente;
        this.isoleBossSconfitto = isoleBossSconfitto;
        this.dataSalvataggio= LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Giocatore getGiocatore() { return giocatore; }
    public void setGiocatore(Giocatore giocatore) { this.giocatore = giocatore; }
    public LocalDateTime getDataSalvataggio() { return dataSalvataggio; }
    public void setDataSalvataggio(LocalDateTime dataSalvataggio) {this.dataSalvataggio = dataSalvataggio;}
    public String getNomeSlot() { return nomeSlot; }
    public String getNomeIsolaCorrente() { return nomeIsolaCorrente; }
    public void setNomeIsolaCorrente(String nome) { this.nomeIsolaCorrente = nome; }
    public String getIsoleBossSconfitto() {return isoleBossSconfitto;}
    public void setIsoleBossSconfitto(String isoleBossSconfitto) {this.isoleBossSconfitto = isoleBossSconfitto;}

    @Override
    public String toString() {return "Salvataggio:'" + nomeSlot + "', data=" + dataSalvataggio + "}";}
}
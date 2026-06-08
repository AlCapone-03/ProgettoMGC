package it.unicam.cs.mpgc.rpg123279.model.oggetti;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.Rarita;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoOggetto;
import jakarta.persistence.*;

@Entity
@Table(name = "oggetti")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractOggetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 1000)
    private String descrizione;

    @Column(nullable = false)
    private int valore;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rarita rarita;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoOggetto tipoOggetto;

    @Column(nullable = false)
    private int livelloRichiesto;

    public AbstractOggetto() {}

    public AbstractOggetto(String nome, String descrizione, int valore, Rarita rarita, TipoOggetto tipoOggetto, int livelloRichiesto) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.valore = valore;
        this.rarita = rarita;
        this.tipoOggetto = tipoOggetto;
        this.livelloRichiesto = livelloRichiesto;
    }

    public Long getId() {return id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getDescrizione() {return descrizione;}
    public void setDescrizione(String descrizione) {this.descrizione = descrizione;}
    public int getValore() {return valore;}
    public Rarita getRarita() {return rarita;}
    public void setRarita(Rarita rarita) {this.rarita = rarita;}
    public TipoOggetto getTipoOggetto() {return tipoOggetto;}

    @Override
    public String toString() {
        return nome + " [" + rarita + "]" + " Lv." + livelloRichiesto;
    }
}
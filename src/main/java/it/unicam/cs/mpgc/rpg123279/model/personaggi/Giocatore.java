package it.unicam.cs.mpgc.rpg123279.model.personaggi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.ClassePersonaggio;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "giocatori")
public class Giocatore extends AbstractPersonaggio {

    @Column(nullable = false)
    private int esperienza;

    @Column(nullable = false)
    private int oro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassePersonaggio classePersonaggio;

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(
            targetEntity = AbstractOggetto.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "giocatore_id")
    private List<AbstractOggetto> inventario =
            new ArrayList<>();

    public Giocatore() {}

    public Giocatore(String nome, int maxHp, int attacco, int difesa, int livello, ClassePersonaggio classePersonaggio) {
        super(nome, maxHp, attacco, difesa, livello);
        this.classePersonaggio = classePersonaggio;
        this.esperienza = 0;
        this.oro = 0;
    }

    public void aggiungiEsperienza(int esperienza) {
        this.esperienza += esperienza;
    }

    public void aggiungiOro(int oro) {
        this.oro += oro;
    }

    public void aggiungiOggetto(AbstractOggetto oggetto) {
        inventario.add(oggetto);
    }

    public boolean rimuoviOggetto(AbstractOggetto oggetto) {
        return inventario.remove(oggetto);
    }

    public int getEsperienza() {
        return esperienza;
    }

    public void setEsperienza(int esperienza) {
        this.esperienza = esperienza;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public ClassePersonaggio getClassePersonaggio() {
        return classePersonaggio;
    }

    public void setClassePersonaggio(ClassePersonaggio classePersonaggio) {
        this.classePersonaggio = classePersonaggio;
    }

    public List<AbstractOggetto> getInventario() {
        return inventario;
    }

    public void setInventario(List<AbstractOggetto> inventario) {
        this.inventario = inventario;
    }

    @Override
    public String toString() {
        return "Giocatore{" + "nome='" + getNome() + '\'' + ", livello=" + getLivello() + ", hp=" + getHp() +
                "/" + getMaxHp() + ", attacco=" + getAttacco() + ", difesa=" + getDifesa() + ", " +
                "esperienza=" + esperienza + ", oro=" + oro + ", classe=" + classePersonaggio + '}';
    }

    @Override
    public void subisciDanno(int damage) {

    }

    @Override
    public boolean isVivo() {
        return false;
    }

    @Override
    public int getAttacco() {
        return 0;
    }

    @Override
    public int getDifesa() {
        return 0;
    }

    @Override
    public int getHp() {
        return 0;
    }

    @Override
    public int getMaxHp() {
        return 0;
    }

    @Override
    public int getLivello() {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public void recuperaSalute(int amount) {

    }
}
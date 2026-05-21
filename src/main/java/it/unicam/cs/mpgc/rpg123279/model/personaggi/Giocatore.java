package it.unicam.cs.mpgc.rpg123279.model.personaggi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.ClassePersonaggio;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractEquipaggiamento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Arma;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Armatura;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.equipaggiamenti.Reliquia;
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
    @OneToMany(targetEntity = AbstractOggetto.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "giocatore_id")
    private List<AbstractOggetto> inventario = new ArrayList<>();

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(targetEntity = AbstractEquipaggiamento.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "giocatore_equipaggiamento_id")
    private List<AbstractEquipaggiamento> equipaggiamenti = new ArrayList<>();

    public Giocatore() {}
    public Giocatore(String nome, int maxHp, int attacco, int difesa, int livello, ClassePersonaggio cp) {
        super(nome, maxHp, attacco, difesa, livello);
        this.classePersonaggio = cp;
        this.esperienza = 0;
        this.oro = 0;
    }

    public void aggiungiEsperienza(int xp) {
        this.esperienza += xp;
    }

    public void aggiungiOro(int oro) {
        this.oro += oro;
    }

    public void aggiungiAInventario(AbstractOggetto oggetto) {
        inventario.add(oggetto);
    }

    public boolean rimuoviDaInventario(AbstractOggetto oggetto) {
        return inventario.remove(oggetto);
    }

    public void equipaggia(AbstractEquipaggiamento equipaggiamento) {
        equipaggiamenti.removeIf(e -> e.getClass().equals(equipaggiamento.getClass()));
        equipaggiamenti.add(equipaggiamento);
    }

    public boolean rimuoviEquipaggiamento(AbstractEquipaggiamento equipaggiamento) {
        return equipaggiamenti.removeIf(e -> e.getClass().equals(equipaggiamento.getClass()));
    }

    public int getEsperienza() {return esperienza;}
    public void setEsperienza(int esperienza) {this.esperienza = esperienza;}
    public int getOro() {return oro;}
    public void setOro(int oro) {this.oro = oro;}
    public ClassePersonaggio getClassePersonaggio() {return classePersonaggio;}
    public void setClassePersonaggio(ClassePersonaggio cp) {this.classePersonaggio = cp;}
    public List<AbstractOggetto> getInventario() {return inventario;}
    public void setInventario(List<AbstractOggetto> inventario) {this.inventario = inventario;}
    public List<AbstractEquipaggiamento> getEquipaggiamenti() {return equipaggiamenti;}
    public Arma getArmaEquipaggiata() {
        return equipaggiamenti.stream()
                .filter(e -> e instanceof Arma)
                .map(e -> (Arma) e).findFirst().orElse(null);
    }
    public Armatura getArmaturaEquipaggiata() {
        return equipaggiamenti.stream()
                .filter(e -> e instanceof Armatura)
                .map(e -> (Armatura) e).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "Giocatore{" + "nome='" + getNome() + '\'' + ", livello=" + getLivello() + ", hp=" + getHp() +
                "/" + getMaxHp() + ", attacco=" + getAttacco() + ", difesa=" + getDifesa() + ", " +
                "esperienza=" + esperienza + ", oro=" + oro + ", classe=" + classePersonaggio + '}';
    }

    @Override
    public int getAttacco() {
        int bonusArmi = inventario.stream().filter(o -> o instanceof Arma)
                .mapToInt(o -> ((Arma) o).getBonusStatistica()).sum();
        return super.getAttacco() + bonusArmi;
    }

    @Override
    public int getDifesa() {
        int bonusDifesa = inventario.stream().filter(o -> o instanceof Armatura || o instanceof Reliquia)
                .mapToInt(o -> ((AbstractEquipaggiamento) o).getBonusStatistica()).sum();
        return super.getDifesa() + bonusDifesa;
    }
}
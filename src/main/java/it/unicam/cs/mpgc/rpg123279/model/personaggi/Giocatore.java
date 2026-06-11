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
    @OneToMany(targetEntity = AbstractOggetto.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "giocatore_id")
    private List<AbstractOggetto> inventario = new ArrayList<>();

    @SuppressWarnings("JpaAttributeTypeInspection")
    @OneToMany(targetEntity = AbstractEquipaggiamento.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "giocatore_equipaggiamento_id")
    private List<AbstractEquipaggiamento> equipaggiamenti = new ArrayList<>();

    public Giocatore() {}
    public Giocatore(String nome, ClassePersonaggio cp) {
        super(nome, cp.getMaxHpBase(), cp.getAttaccoBase(), cp.getDifesaBase(), 1);
        this.classePersonaggio = cp;
        this.esperienza = 0;
        this.oro = 0;
    }

    public int getAttaccoLivello() { return super.getAttacco(); }
    public int getDifesaLivello() { return super.getDifesa(); }

    public void aggiungiEsperienza(int xp) {this.esperienza += xp;}

    public void aggiungiOro(int oro) {this.oro += oro;}

    public void aggiungiAInventario(AbstractOggetto oggetto) {inventario.add(oggetto);}

    public boolean rimuoviDaInventario(AbstractOggetto oggetto) {
        boolean rimosso = inventario.remove(oggetto);
        if (rimosso && oggetto instanceof AbstractEquipaggiamento) {
            equipaggiamenti.remove((AbstractEquipaggiamento) oggetto);
        }
        return rimosso;
    }

    public void equipaggia(AbstractEquipaggiamento equipaggiamento) {
        equipaggiamenti.removeIf(e -> e.getClass().equals(equipaggiamento.getClass()));
        equipaggiamenti.add(equipaggiamento);
    }

    public int getEsperienza() {return esperienza;}
    public int getOro() {return oro;}
    public void setOro(int oro) {this.oro = oro;}
    public ClassePersonaggio getClassePersonaggio() {return classePersonaggio;}
    public List<AbstractOggetto> getInventario() {return inventario;}
    public List<AbstractEquipaggiamento> getEquipaggiamenti() {return equipaggiamenti;}
    public Arma getArmaEquipaggiata() {
        return equipaggiamenti.stream().filter(e -> e instanceof Arma)
                .map(e -> (Arma) e).findFirst().orElse(null);
    }
    public Armatura getArmaturaEquipaggiata() {
        return equipaggiamenti.stream().filter(e -> e instanceof Armatura)
                .map(e -> (Armatura) e).findFirst().orElse(null);
    }

    public Reliquia getReliquiaEquipaggiata() {
        return equipaggiamenti.stream().filter(e -> e instanceof Reliquia)
                .map(e -> (Reliquia) e).findFirst().orElse(null);
    }

    @Override
    public int getMaxHp() {
        Reliquia reliquia = getReliquiaEquipaggiata();
        int bonusReliquia = reliquia != null ? reliquia.getBonusStatistica() : 0;
        return super.getMaxHp() + bonusReliquia;
    }

    @Override
    public int getAttacco() {
        Arma armaEquipaggiata = getArmaEquipaggiata();
        int bonusArma = armaEquipaggiata != null ? armaEquipaggiata.getBonusStatistica() : 0;
        return super.getAttacco() + bonusArma;
    }

    @Override
    public int getDifesa() {
        Armatura armaturaEquipaggiata = getArmaturaEquipaggiata();
        int bonusArmatura = armaturaEquipaggiata != null ? armaturaEquipaggiata.getBonusStatistica() : 0;
        return super.getDifesa() + bonusArmatura;
    }

    @Override
    public String toString() {
        return "Giocatore{" + "nome='" + getNome() + '\'' + ", livello=" + getLivello() + ", hp=" + getHp() +
                "/" + getMaxHp() + ", attacco=" + getAttacco() + ", difesa=" + getDifesa() + ", " +
                "esperienza=" + esperienza + ", oro=" + oro + ", classe=" + classePersonaggio + '}';
    }
}
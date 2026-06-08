package it.unicam.cs.mpgc.rpg123279.model.mappe;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.nemici.KrakenBoss;
import java.util.ArrayList;
import java.util.List;

public class Isola {

    private String nome;
    private String descrizione;
    private int livelloConsigliato;
    private int ordineSequenza;
    private KrakenBoss boss;
    private boolean bossSconfitto = false;
    private boolean sbloccata = false;
    private List<Class<? extends AbstractNemico>> nemiciPossibili = new ArrayList<>();

    public Isola() {}
    public Isola(String nome, String descrizione, int livelloConsigliato, int ordineSequenza, int livelloBoss) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.livelloConsigliato = livelloConsigliato;
        this.ordineSequenza = ordineSequenza;
        this.boss = new KrakenBoss(livelloBoss);
    }

    public void aggiungiNemicoPossibile(Class<? extends AbstractNemico> nemico) {
        nemiciPossibili.add(nemico);
    }

    public String getNome(){ return nome; }
    public String getDescrizione(){ return descrizione; }
    public int getLivelloConsigliato(){ return livelloConsigliato; }
    public int getOrdineSequenza(){ return ordineSequenza; }
    public KrakenBoss getBoss(){ return boss; }
    public boolean isBossSconfitto(){ return bossSconfitto; }
    public boolean isSbloccata(){ return sbloccata; }
    public void setBossSconfitto(boolean v){ this.bossSconfitto = v; }
    public void setSbloccata(boolean v){ this.sbloccata = v; }

    public List<Class<? extends AbstractNemico>> getNemiciPossibili() {
        return nemiciPossibili;
    }

    @Override
    public String toString() {
        return nome + " (Lv." + livelloConsigliato + ")";
    }
}
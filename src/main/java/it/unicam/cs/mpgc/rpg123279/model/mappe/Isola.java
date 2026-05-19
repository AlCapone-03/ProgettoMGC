package it.unicam.cs.mpgc.rpg123279.model.mappe;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import java.util.ArrayList;
import java.util.List;

public class Isola {

    private String nome;
    private String descrizione;
    private int livelloConsigliato;
    private List<Class<? extends AbstractNemico>> nemiciPossibili = new ArrayList<>();

    public Isola() {}
    public Isola(String nome, String descrizione, int livelloConsigliato) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.livelloConsigliato = livelloConsigliato;
    }

    public void aggiungiNemicoPossibile(Class<? extends AbstractNemico> nemico) {
        nemiciPossibili.add(nemico);
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getLivelloConsigliato() {
        return livelloConsigliato;
    }

    public List<Class<? extends AbstractNemico>> getNemiciPossibili() {
        return nemiciPossibili;
    }

    @Override
    public String toString() {
        return nome + " (Lv." + livelloConsigliato + ")";
    }
}
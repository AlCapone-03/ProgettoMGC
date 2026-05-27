package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.Costanti;

public class ServiceLivello implements IServiceLivello {

    @Override
    public void aggiungiEsperienza(Giocatore giocatore, int xp) {
        if (xp <= 0) return;
        giocatore.aggiungiEsperienza(xp);
        while (saleDiLivello(giocatore)) {
            applicaLevelUp(giocatore);
        }
    }

    @Override
    public boolean saleDiLivello(Giocatore giocatore) {
        if (giocatore.getLivello() >= Costanti.MAX_LIVELLO) return false;
        return giocatore.getEsperienza() >= getXpLivelloSuccessivo(giocatore.getLivello() + 1);
    }

    @Override
    public int getXpLivelloSuccessivo(int livello) {
        if (livello <= 1) return 0;
        return Costanti.XP_LIVELLO_BASE * (livello - 1) * livello / 2;
    }

    private void applicaLevelUp(Giocatore giocatore) {
        giocatore.setLivello(giocatore.getLivello() + 1);
        giocatore.setAttacco(giocatore.getAttacco() + Costanti.ATTACCO_PER_LIVELLO);
        giocatore.setDifesa(giocatore.getDifesa() + Costanti.DIFESA_PER_LIVELLO);
        giocatore.setMaxHp(giocatore.getMaxHp() + Costanti.HP_PER_LIVELLO);
        System.out.println(giocatore.getNome() + " è ora al livello " + giocatore.getLivello());
    }
}
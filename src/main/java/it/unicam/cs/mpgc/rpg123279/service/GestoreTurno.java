package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.model.battaglia.BattleState;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.DatiTurno;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.AzioneCombattimento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.Costanti;
import java.util.Random;
public class GestoreTurno {

    private final IServiceInventario serviceInventario;
    private final Random random;

    public GestoreTurno(IServiceInventario serviceInventario) {
        this.serviceInventario= serviceInventario;
        this.random= new Random();
    }

    public DatiTurno eseguiTurno(BattleState stato, AzioneCombattimento azione, AbstractOggetto oggettoScelto) {
        return switch (azione) {
            case ATTACCA -> gestisciAttacco(stato);
            case USA_OGGETTO -> gestisciUsaOggetto(stato, oggettoScelto);
            case SCAPPA -> gestisciFuga(stato);
        };
    }

    private DatiTurno gestisciAttacco(BattleState stato) {
        return gestisciAttaccoGiocatore(stato.getGiocatore(), stato.getNemico());
    }

    private DatiTurno gestisciAttaccoGiocatore(Giocatore giocatore, AbstractNemico nemico) {
        StringBuilder desc = new StringBuilder();
        int dannoAlNemico = calcolaDanno(giocatore.getAttacco(), nemico.getDifesa());
        nemico.setHp(nemico.getHp() - dannoAlNemico);
        desc.append(giocatore.getNome()).append(" attacca ").append(nemico.getNome()).append(" infliggendo ")
                .append(dannoAlNemico).append(" danni.");
        int dannoAlGiocatore = 0;
        if (nemico.isVivo()) {
            dannoAlGiocatore = attaccoNemico(giocatore, nemico, desc, "contrattacca");
        } else {
            desc.append(" ").append(nemico.getNome()).append(" è stato sconfitto!");
        }
        return new DatiTurno.Builder(AzioneCombattimento.ATTACCA).dannoInflitto(dannoAlNemico)
                .dannoRicevuto(dannoAlGiocatore).descrizione(desc.toString()).build();
    }

    private int attaccoNemico(Giocatore giocatore, AbstractNemico nemico, StringBuilder desc, String azione) {
        int dannoAlGiocatore = calcolaDanno(nemico.getAttacco(), giocatore.getDifesa());
        giocatore.setHp(giocatore.getHp() - dannoAlGiocatore);
        desc.append(" ").append(nemico.getNome())
                .append(" ").append(azione).append(" per ").append(dannoAlGiocatore).append(" danni.");
        return dannoAlGiocatore;
    }

    private DatiTurno gestisciUsaOggetto(BattleState stato, AbstractOggetto oggetto) {
        Giocatore giocatore= stato.getGiocatore();
        if (oggetto == null) {
            return new DatiTurno.Builder(AzioneCombattimento.USA_OGGETTO)
                    .descrizione("Nessun oggetto selezionato. Il turno è sprecato.").build();
        }
        String desc = giocatore.getNome() + " usa " + oggetto.getNome() + ".";
        serviceInventario.usaOggetto(giocatore, oggetto);
        return new DatiTurno.Builder(AzioneCombattimento.USA_OGGETTO).oggettoUsato(oggetto)
                .descrizione(desc).build();
    }

    private DatiTurno gestisciFuga(BattleState stato) {
        boolean fugaRiuscita= random.nextDouble() < Costanti.PROBABILITA_FUGA;
        if (fugaRiuscita) {
            stato.terminaBattaglia();
            String desc = stato.getGiocatore().getNome() + " fugge con successo!";
            return new DatiTurno.Builder(AzioneCombattimento.SCAPPA).fugaRiuscita(true).descrizione(desc).build();
        }
        Giocatore giocatore= stato.getGiocatore();
        AbstractNemico nemico = stato.getNemico();
        StringBuilder desc = new StringBuilder(giocatore.getNome()).append(" tenta di fuggire ma fallisce!");
        int dannoAlGiocatore = attaccoNemico(giocatore, nemico, desc, "colpisce");
        return new DatiTurno.Builder(AzioneCombattimento.SCAPPA).fugaRiuscita(false)
                .dannoRicevuto(dannoAlGiocatore).descrizione(desc.toString()).build();
    }

    private int calcolaDanno(int attacco, int difesa) {
        return Math.max(0, attacco - difesa);
    }
}
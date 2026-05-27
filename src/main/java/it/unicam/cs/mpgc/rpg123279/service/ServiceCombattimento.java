package it.unicam.cs.mpgc.rpg123279.service;

import it.unicam.cs.mpgc.rpg123279.exception.CombattimentoException;
import it.unicam.cs.mpgc.rpg123279.factory.IFactoryOggetto;
import it.unicam.cs.mpgc.rpg123279.model.battaglia.*;
import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.AzioneCombattimento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import it.unicam.cs.mpgc.rpg123279.Costanti;
import java.util.Random;

public class ServiceCombattimento implements IServiceCombattimento {

    private final GestoreTurno gestoreTurno;
    private final IServiceLivello serviceLivello;
    private final IServiceInventario serviceInventario;
    private final IFactoryOggetto factoryOggetto;
    private final Random random;

    public ServiceCombattimento(GestoreTurno gestoreTurno, IServiceLivello serviceLivello,
                                IServiceInventario serviceInventario, IFactoryOggetto factoryOggetto) {
        this.gestoreTurno= gestoreTurno;
        this.serviceLivello= serviceLivello;
        this.serviceInventario= serviceInventario;
        this.factoryOggetto= factoryOggetto;
        this.random= new Random();
    }

    @Override
    public BattleState iniziaBattaglia(Giocatore giocatore, AbstractNemico nemico) {
        return new BattleState(giocatore, nemico);
    }

    @Override
    public DatiTurno eseguiTurno(BattleState stato, AzioneCombattimento azione, AbstractOggetto oggettoScelto) {
        if (stato.isBattagliaFinita()) {
            throw new CombattimentoException("La battaglia è già terminata.");
        }
        DatiTurno turno = gestoreTurno.eseguiTurno(stato, azione, oggettoScelto);
        stato.aggiungiLogTurno(turno.getDescrizione());
        if (stato.isBattagliaFinita()) {
            stato.terminaBattaglia();
        } else {
            stato.avanzaTurno();
        }
        return turno;
    }

    @Override
    public RisultatoBattaglia concludiBattaglia(BattleState stato) {
        Giocatore giocatore= stato.getGiocatore();
        AbstractNemico nemico= stato.getNemico();
        if (giocatore.isVivo() && nemico.isVivo()) {return RisultatoBattaglia.fuga(stato.getLogBattaglia());}
        if (!giocatore.isVivo()) {
            stato.aggiungiLog(giocatore.getNome() + " è stato sconfitto.");
            return RisultatoBattaglia.sconfitta(stato.getLogBattaglia());
        }
        int xp  = nemico.getXpRilasciata();
        int oro = nemico.getOroRilasciato();
        AbstractOggetto drop = dropOggetto(giocatore.getLivello());
        serviceLivello.aggiungiEsperienza(giocatore, xp);
        giocatore.aggiungiOro(oro);
        if (drop != null) {
            serviceInventario.aggiungiOggetto(giocatore, drop);
        }
        stato.aggiungiLog("Vittoria! XP: +" + xp + "  Oro: +" + oro +
                (drop != null ? "  Drop: " + drop.getNome() : ""));
        return RisultatoBattaglia.vittoria(xp, oro, drop, stato.getLogBattaglia());
    }

    private AbstractOggetto dropOggetto(int livelloGiocatore) {
        if (random.nextDouble() < Costanti.PROBABILITA_DROP) {
            return factoryOggetto.creaOggettoCasuale(livelloGiocatore);
        }
        return null;
    }
}
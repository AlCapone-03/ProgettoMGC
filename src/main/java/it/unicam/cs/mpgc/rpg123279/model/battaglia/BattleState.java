package it.unicam.cs.mpgc.rpg123279.model.battaglia;

import it.unicam.cs.mpgc.rpg123279.model.personaggi.AbstractNemico;
import it.unicam.cs.mpgc.rpg123279.model.personaggi.Giocatore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleState {

    private final Giocatore giocatore;
    private final AbstractNemico nemico;
    private int numeroTurno;
    private boolean battagliaAttiva;
    private boolean turnoGiocatore;
    private final List<String> logBattaglia;

    public BattleState(Giocatore giocatore, AbstractNemico nemico) {
        if (giocatore == null) throw new IllegalArgumentException("Il giocatore non può essere null.");
        if (nemico == null) throw new IllegalArgumentException("Il nemico non può essere null.");
        this.giocatore= giocatore;
        this.nemico= nemico;
        this.numeroTurno= 1;
        this.battagliaAttiva= true;
        this.turnoGiocatore= true;
        this.logBattaglia= new ArrayList<>();
        aggiungiLog("La battaglia ha inizio: " + giocatore.getNome() + " vs " + nemico.getNome());
    }

    public void avanzaTurno() {
        numeroTurno++;
        turnoGiocatore = !turnoGiocatore;
    }

    public void terminaBattaglia() {
        this.battagliaAttiva = false;
    }

    public void aggiungiLog(String messaggio) {logBattaglia.add(messaggio);}
    public void aggiungiLogTurno(String messaggio) {logBattaglia.add("[Turno " + numeroTurno + "] " + messaggio);}

    public Giocatore getGiocatore(){ return giocatore; }
    public AbstractNemico getNemico(){ return nemico; }
    public int getNumeroTurno(){ return numeroTurno; }
    public boolean isBattagliaAttiva(){ return battagliaAttiva; }
    public boolean isTurnoGiocatore(){ return turnoGiocatore; }
    public List<String> getLogBattaglia(){ return Collections.unmodifiableList(logBattaglia); }
}
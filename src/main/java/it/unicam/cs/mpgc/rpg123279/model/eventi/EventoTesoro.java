package it.unicam.cs.mpgc.rpg123279.model.eventi;

import it.unicam.cs.mpgc.rpg123279.model.enumerazioni.TipoEvento;
import it.unicam.cs.mpgc.rpg123279.model.oggetti.AbstractOggetto;

public class EventoTesoro extends AbstractGameEvent{

    private final AbstractOggetto oggetto;
    private final int oro;

    public EventoTesoro(AbstractOggetto oggetto, int oro) {
        super(TipoEvento.TESORO, getDescrizioneEvento(oggetto, oro));
        this.oggetto= oggetto;
        this.oro= oro;
    }

    private static String getDescrizioneEvento(AbstractOggetto oggetto, int oro) {
        if (oggetto != null) return "Hai trovato un forziere con " + oggetto.getNome() + " e " + oro + " monete d'oro.";
        return "Hai trovato un forziere con " + oro + " monete d'oro.";
    }

    public AbstractOggetto getOggetto(){ return oggetto; }
    public int getOro(){ return oro; }
//    public boolean haOggetto(){ return oggetto != null; }
//    public boolean haOro(){ return oro>0; }
}